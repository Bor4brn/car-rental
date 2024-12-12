package com.boraandege.carrental.service;

import com.boraandege.carrental.dto.MemberDTO;
import java.util.List;

public interface MemberService {


    MemberDTO registerMember(MemberDTO memberDTO);

    MemberDTO getMemberById(Long id);

    List<MemberDTO> getAllMembers();

    MemberDTO updateMember(Long id, MemberDTO memberDTO);

    void deleteMember(Long id);
}
