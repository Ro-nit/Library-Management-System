package org.example.service;

import org.example.dto.IssueResponseDto;
import org.example.dto.MemberIssueDto;
import org.example.entity.Book;
import org.example.entity.Issue;
import org.example.entity.Member;
import org.example.repository.BookRepo;
import org.example.repository.IssueRepo;
import org.example.repository.MemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class IssueService {

    @Autowired
    private IssueRepo issueRepo;
    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private MemberRepo memberRepo;


    // GET ISSUE BEFORE DTO
//    public List<Issue> getIssue(){
//        return issueRepo.findAll();
//    }

    // GET ISSUE AFTER DTO
    public List<IssueResponseDto> getIssue(){
        List<Issue> issueList = issueRepo.findAll();
        List<IssueResponseDto> dtoList = new ArrayList<>();

        for(Issue issue: issueList){
            IssueResponseDto dto = convertIntoDto(issue);
            dtoList.add(dto);
        }
        return dtoList;
    }

    // GET MEMBER'S ACTIVE ISSUES
    public List<MemberIssueDto> getMemberIssues(int memberId) {
        List<Issue> issueList = issueRepo.findByMember_MemberIdAndReturnDateIsNull(memberId);
        List<MemberIssueDto> dtoList = new ArrayList<>();

        for (Issue issue: issueList) {
            MemberIssueDto dto = new MemberIssueDto(
                    issue.getBook().getBookId(),
                    issue.getBook().getBookTitle(),
                    issue.getIssueDate()
            );
            dtoList.add(dto);
        }
        return dtoList;
    }

    // CONVERTING INTO DTO
    public IssueResponseDto convertIntoDto(Issue issue){
        IssueResponseDto issueResponseDto = new IssueResponseDto(
                issue.getIssueId(),
                issue.getBook().getBookTitle(),
                issue.getMember().getMemberName(),
                issue.getIssueDate(),
                issue.getReturnDate()
        );
        return issueResponseDto;
    }

    // ADD ISSUE AFTER DTO
    @Transactional
    public IssueResponseDto addIssue(int memberId, int bookId) {
        try {
            List<Member> members = memberRepo.findAll();
            List<Book> books = bookRepo.findAll();

            for (Member member : members) {
                if (member.getMemberId() == memberId) {
                    for (Book book : books) {
                        if (book.getBookId() == bookId){
                            if(book.getBookCopiesAvailable() > 0){
                                Issue issue = new Issue();
                                issue.setBook(book);
                                issue.setMember(member);
                                issue.setIssueDate(LocalDate.now());

                                book.setBookCopiesAvailable(book.getBookCopiesAvailable() - 1);

                                Issue newIssue = issueRepo.save(issue);
                                return convertIntoDto(newIssue);
                            }
                            throw new RuntimeException("copies not available");
                        }
                    }
                    throw new RuntimeException("book not found");
                }
            }
            throw new RuntimeException("member not found");
        }
        catch (RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    // UPDATE ISSUE USING MEMBER AND BOOK ID
    @Transactional
    public IssueResponseDto updateIssue(int memberId, int bookId) {

        List<Issue> issues = issueRepo.findAll();

        for (Issue issue : issues) {

            if (issue.getMember().getMemberId() == memberId &&
                    issue.getBook().getBookId() == bookId &&
                    issue.getReturnDate() == null) {

                    issue.setReturnDate(LocalDate.now());

                    Book book = issue.getBook();
                    book.setBookCopiesAvailable(book.getBookCopiesAvailable() + 1);

                    return convertIntoDto(issue);
            }
        }

        throw new RuntimeException("No active issue with this member and book");
    }

    //UPDATE ISSUE USING ISSUE ID
//    @Transactional
//    public IssueResponseDto updateIssue(int issueId){
//
//        List<Issue> issues = issueRepo.findAll();
//        for(Issue issue: issues){
//            if(issue.getIssueId() == issueId){
//                if(issue.getReturnDate() == null){
//                    issue.setReturnDate(LocalDate.now());
//
//                    Book book = issue.getBook();
//                    book.setBookCopiesAvailable(book.getBookCopiesAvailable() + 1);
//
//                    return convertIntoDto(issue);
//                }
//                throw new RuntimeException("book already returned");
//            }
//        }
//        throw new RuntimeException("no Such issue");
//    }

}
