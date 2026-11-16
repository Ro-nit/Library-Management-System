package org.example.service;

import org.example.entity.Member;
import org.example.repository.MemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepo memberRepo;

    public Member getMember(int id) {
        Optional<Member> member = memberRepo.findById(id);
        if (member.isPresent()) {
            return member.get();
        }
        else throw new RuntimeException("Member not found");

    }

    public List<Member> getMembers(){
        return memberRepo.findAll();
    }

    public Member addMember(Member member){
        return memberRepo.save(member);
    }
}
