package okestro.mission1.service;

import okestro.mission1.entity.*;
import okestro.mission1.exception.custom.NotExistException;
import okestro.mission1.repository.MemberRepository;
import okestro.mission1.repository.NetworkRepository;
import okestro.mission1.repository.VmRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@SpringBootTest
@Transactional
@ActiveProfiles("test")
class VmServiceTest {

    @Autowired
    private VmService vmService;

    @Autowired
    private VmRepository vmRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private NetworkRepository networkRepository;

    @BeforeEach
    void setUp() {
        Member saveMember = Member.builder()
                .email("test@email.com")
                .password("1q2w3e4R!")
                .build();

        Network saveNetwork = Network.builder()
                .title("Test Network")
                .openIp("1.1.1.2")
                .openPort(10000).build();

        memberRepository.save(saveMember);
        networkRepository.save(saveNetwork);

        vmRepository.deleteAll();
        vmRepository.saveAll(
                List.of(
                        Vm.builder()
                                .member(saveMember)
                                .vmStatus(VmStatus.STARTING)
                                .title("vm1")
                                .description("Test VM 1")
                                .vCpu(4)
                                .memory(16)
                                .storage(4)
                                .member(saveMember)
                                .privateIp("1.1.1.1")
                                .network(List.of(saveNetwork))
                                .deleted(false)
                                .build(),
                        Vm.builder()
                                .member(saveMember)
                                .vmStatus(VmStatus.RUNNING)
                                .title("vm2")
                                .description("Test VM 2")
                                .vCpu(4)
                                .memory(20)
                                .storage(8)
                                .member(saveMember)
                                .privateIp("2.2.2.2")
                                .network(List.of(saveNetwork))
                                .deleted(false)
                                .build(),
                        Vm.builder()
                                .member(saveMember)
                                .vmStatus(VmStatus.PENDING)
                                .title("vm3")
                                .description("Test VM 3")
                                .vCpu(8)
                                .memory(16)
                                .storage(20)
                                .member(saveMember)
                                .privateIp("3.3.3.3")
                                .network(List.of(saveNetwork))
                                .deleted(false)
                                .build(),
                        Vm.builder()
                                .member(saveMember)
                                .vmStatus(VmStatus.TERMINATED)
                                .title("vm4")
                                .description("Test VM 4")
                                .vCpu(4)
                                .memory(4)
                                .storage(20)
                                .member(saveMember)
                                .privateIp("4.4.4.4")
                                .network(List.of(saveNetwork))
                                .deleted(false)
                                .build()
                )
        );
    }

    @Nested
    class 가상머신_id가_주어지고 {
        @Test
        void id가_DB에_존재하지_않는다면_예외를_발생시킨다() {
            //given
            int notExistingVmId = -1;

            //when & then
            Assertions.assertThatThrownBy(() -> vmService.findVm(notExistingVmId)).isInstanceOf(NotExistException.class);
        }

        @Test
        void id가_DB에_존재한다면_VM을_반환한다() {
            //given
            int existVmId = vmRepository.findAll().stream().findFirst()
                    .orElseThrow(() -> new NotExistException("인자가 잘못되었습니다."))
                    .getVmId();

            //when
            Vm findVm = vmService.findVm(existVmId);

            //then
            Assertions.assertThat(findVm).isNotNull();

        }
    }

}