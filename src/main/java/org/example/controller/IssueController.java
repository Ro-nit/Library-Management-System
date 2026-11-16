package org.example.controller;

import org.example.dto.IssueResponseDto;
import org.example.dto.MemberIssueDto;
import org.example.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class IssueController {

    @Autowired
    private IssueService issueService;

    // GET ISSUE BEFORE DTO
//    @GetMapping("/issues")
//    public List<Issue> getIssue(){
//        return issueService.getIssue();
//    }

    // ADD ISSUE BEFORE DTO
//    @PostMapping("/issues")
//    public Issue addIssue(@RequestBody Map<String, Integer> issueRequest){
//        int memberId = issueRequest.get("memberId");
//        int bookId = issueRequest.get("bookId");
//        return issueService.addIssue(memberId, bookId);
//    }

    // GET ISSUE AFTER DTO
    @GetMapping("/issues")
    public List<IssueResponseDto> getIssue(){
        return issueService.getIssue();
    }

    // ADD ISSUE AFTER DTO
    @PostMapping("/issues")
    public IssueResponseDto addIssue(@RequestBody Map<String, Integer> issueRequest){
        int memberId = issueRequest.get("memberId");
        int bookId = issueRequest.get("bookId");
        return issueService.addIssue(memberId, bookId);
    }

    // UPDATE ISSUE USING MEMBER AND BOOK ID AFTER DTO
    @PostMapping("/issues/return")
    public IssueResponseDto updateIssue(@RequestBody Map<String, Integer> updateRequest){
        int memberId = updateRequest.get("memberId");
        int bookId = updateRequest.get("bookId");
        return issueService.updateIssue(memberId, bookId);
    }

    // UPDATE ISSUE USING ISSUE ID AFTER DTO
//    @PutMapping("/issues")
//    public IssueResponseDto updateIssue(@RequestBody Map<String, Integer> updateRequest){
//        int issueId = updateRequest.get("issueId");
//        return issueService.updateIssue(issueId);
//    }


    @GetMapping("/issues/member/{memberId}")
    public List<MemberIssueDto> getMemberIssues(@PathVariable int memberId) {
        return issueService.getMemberIssues(memberId);
    }
}
