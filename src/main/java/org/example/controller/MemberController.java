package org.example.controller;

import org.example.entity.Member;
import org.example.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/members/{id}")
    public Member getMember(@PathVariable int id) {
        return memberService.getMember(id);
    }

    @GetMapping("/members")
    public List<Member> getMembers(){
        return memberService.getMembers();
    }

    @PostMapping("/members")
    public Member addMember(@RequestBody Member member){
        return memberService.addMember(member);
    }
}
