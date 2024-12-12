package com.boraandege.carrental;

import com.boraandege.carrental.dto.MemberDTO;
import com.boraandege.carrental.model.Member;
import com.boraandege.carrental.exception.BusinessException;
import com.boraandege.carrental.mapper.MemberMapper;
import com.boraandege.carrental.repository.MemberRepository;
import com.boraandege.carrental.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
class MemberServiceImplTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MemberServiceImpl memberService;


    @Test
    void testRegisterMember_Success() {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setName("John Doe");
        memberDTO.setDrivingLicenseNumber("DL123");

        MemberDTO result = memberService.registerMember(memberDTO);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());

        Member savedMember = memberRepository.findById(result.getId()).orElseThrow();
        assertEquals("DL123", savedMember.getDrivingLicenseNumber());
    }

    @Test
    void testRegisterMember_DuplicateLicense() {
        Member member = new Member();
        member.setName("John Doe");
        member.setDrivingLicenseNumber("DL123");
        memberRepository.save(member);

        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setName("Jane Doe");
        memberDTO.setDrivingLicenseNumber("DL123");

        assertThrows(BusinessException.class, () -> memberService.registerMember(memberDTO));
    }

    @Test
    void testGetMemberById() {
        Member member = new Member();
        member.setName("John Doe");
        member.setDrivingLicenseNumber("DL123");
        Member savedMember = memberRepository.save(member);

        MemberDTO result = memberService.getMemberById(savedMember.getId());

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("DL123", result.getDrivingLicenseNumber());
    }

    @Test
    void testGetAllMembers() {
        Member member1 = new Member();
        member1.setName("John Doe");
        member1.setDrivingLicenseNumber("DL123");

        Member member2 = new Member();
        member2.setName("Jane Smith");
        member2.setDrivingLicenseNumber("DL456");

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<MemberDTO> result = memberService.getAllMembers();

        assertEquals(3, result.size());
        assertTrue(result.stream().anyMatch(m -> "John Doe".equals(m.getName())));
        assertTrue(result.stream().anyMatch(m -> "Jane Smith".equals(m.getName())));
    }
    @Test
    void testUpdateMember() {
        Member member = new Member();
        member.setName("John Doe");
        member.setDrivingLicenseNumber("DL123");
        Member savedMember = memberRepository.save(member);

        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setName("John Doe Updated");
        memberDTO.setEmail("john@example.com");

        MemberDTO result = memberService.updateMember(savedMember.getId(), memberDTO);

        assertNotNull(result);
        assertEquals("John Doe Updated", result.getName());
        assertEquals("john@example.com", result.getEmail());
    }

    @Test
    void testDeleteMember() {
        Member member = new Member();
        member.setName("John Doe");
        member.setDrivingLicenseNumber("DL123");
        Member savedMember = memberRepository.save(member);

        memberService.deleteMember(savedMember.getId());

        assertFalse(memberRepository.findById(savedMember.getId()).isPresent());
    }
}

