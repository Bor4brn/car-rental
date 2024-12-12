package com.boraandege.carrental.service.impl;

import com.boraandege.carrental.dto.MemberDTO;
import com.boraandege.carrental.model.Member;
import com.boraandege.carrental.exception.BusinessException;
import com.boraandege.carrental.exception.ResourceNotFoundException;
import com.boraandege.carrental.mapper.MemberMapper;
import com.boraandege.carrental.repository.MemberRepository;
import com.boraandege.carrental.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository, MemberMapper memberMapper) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }

    @Override
    public MemberDTO registerMember(MemberDTO memberDTO) {
        if (memberRepository.existsByDrivingLicenseNumber(memberDTO.getDrivingLicenseNumber())) {
            throw new BusinessException("Member with this driving license number already exists");
        }
        Member member = memberMapper.toEntity(memberDTO);
        Member savedMember = memberRepository.save(member);
        return memberMapper.toDTO(savedMember);
    }

    @Override
    public MemberDTO getMemberById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));
        return memberMapper.toDTO(member);
    }

    @Override
    public List<MemberDTO> getAllMembers() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map(memberMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MemberDTO updateMember(Long id, MemberDTO memberDTO) {
        Member existingMember = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));

        existingMember.setName(memberDTO.getName());
        existingMember.setAddress(memberDTO.getAddress());
        existingMember.setEmail(memberDTO.getEmail());
        existingMember.setPhone(memberDTO.getPhone());
        Member updatedMember = memberRepository.save(existingMember);
        return memberMapper.toDTO(updatedMember);
    }

    @Override
    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));
        memberRepository.delete(member);
    }
}
