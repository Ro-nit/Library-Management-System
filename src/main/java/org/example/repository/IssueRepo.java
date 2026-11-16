package org.example.repository;

import org.example.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepo extends JpaRepository<Issue, Integer> {
    List<Issue> findByMember_MemberIdAndReturnDateIsNull(int memberId);
}
