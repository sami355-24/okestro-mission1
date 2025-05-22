package okestro.mission1.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import okestro.mission1.dto.request.CreateVmRequest;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


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

    @PersistenceContext
    EntityManager em;

    Member saveMember;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
        networkRepository.deleteAll();
        vmRepository.deleteAll();

        Member saveMember = Member.builder()
                .email("test@email.com")
                .password("1q2w3e4R!")
                .build();

        Network saveNetwork = Network.builder()
                .name("Test Network")
                .openIp("1.1.1.2")
                .openPort(10000).build();

        this.saveMember = saveMember;

        memberRepository.save(saveMember);
        networkRepository.save(saveNetwork);

        vmRepository.saveAll(
                List.of(
                        Vm.builder()
                                .member(saveMember)
                                .vmStatus(VmStatus.STARTING)
                                .name("vm1")
                                .description("Test VM 1")
                                .vCpu(4)
                                .memory(16)
                                .storage(4)
                                .member(saveMember)
                                .privateIp("1.1.1.1")
                                .networks(List.of(saveNetwork))
                                .deleted(false)
                                .build(),
                        Vm.builder()
                                .member(saveMember)
                                .vmStatus(VmStatus.RUNNING)
                                .name("vm2")
                                .description("Test VM 2")
                                .vCpu(4)
                                .memory(20)
                                .storage(8)
                                .member(saveMember)
                                .privateIp("2.2.2.2")
                                .networks(List.of(saveNetwork))
                                .deleted(false)
                                .build(),
                        Vm.builder()
                                .member(saveMember)
                                .vmStatus(VmStatus.PENDING)
                                .name("vm3")
                                .description("Test VM 3")
                                .vCpu(8)
                                .memory(16)
                                .storage(20)
                                .member(saveMember)
                                .privateIp("3.3.3.3")
                                .networks(List.of(saveNetwork))
                                .deleted(false)
                                .build(),
                        Vm.builder()
                                .member(saveMember)
                                .vmStatus(VmStatus.TERMINATED)
                                .name("vm4")
                                .description("Test VM 4")
                                .vCpu(4)
                                .memory(4)
                                .storage(20)
                                .member(saveMember)
                                .privateIp("4.4.4.4")
                                .networks(List.of(saveNetwork))
                                .deleted(false)
                                .build(),
                        Vm.builder()
                                .member(saveMember)
                                .vmStatus(VmStatus.TERMINATED)
                                .name("vm-duplicate")
                                .description("duplicate VM 4")
                                .vCpu(4)
                                .memory(4)
                                .storage(20)
                                .member(saveMember)
                                .privateIp("5.5.5.5")
                                .networks(List.of(saveNetwork))
                                .deleted(false)
                                .build()
                )
        );
    }

    @Nested
    class 가상머신_조회시도시 {
        @Test
        void id가_DB에_존재하지_않는다면_예외를_발생시킨다() {
            //given
            int notExistingVmId = -1;

            //when & then
            assertThatThrownBy(() -> vmService.findVm(notExistingVmId)).isInstanceOf(NotExistException.class);
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
            assertThat(findVm).isNotNull();

        }
    }

    @Nested
    class 가상머신_이름_중복체크시도시 {
        @Test
        void 기존_가상머신_이름과_중복된다면_true를_반환한다() {
            //given
            String duplicateVmTitle = "vm-duplicate";

            //when
            boolean result = vmService.isDuplicate(duplicateVmTitle);

            //then
            assertThat(result).isTrue();
        }

        @Test
        void 기존_가상머신_이름과_중복된지_않으면_false를_반환한다() {
            //given
            String originVmTitle = "vm-origin";

            //when
            boolean result = vmService.isDuplicate(originVmTitle);

            //then
            assertThat(result).isFalse();
        }
    }

    @Nested
    class 가상머신_생성_시도를_할떄 {
        String originVmName = "vm-origin";
        String originVmDescription = "vm-origin-description";
        int validVcpu = 4;
        int validMemory = 16;
        int validStorage = 4;
        int validNetworkId;


        @Test
        void 중복된_이름으로_생성_시도시_예외가_발생한다() {
            //given
            String duplicateVmName = "vm-duplicate";
            CreateVmRequest duplicateVmNameRequest = new CreateVmRequest(duplicateVmName, originVmDescription, validVcpu, validMemory, validStorage, List.of(validNetworkId), null);

            //when & then
            Assertions.assertThatThrownBy(() -> vmService.createVmFrom(duplicateVmNameRequest, saveMember)).isInstanceOf(DataIntegrityViolationException.class);
        }

        @Test
        void 필수로_들어가야하는_데이터가_null일때_예외가_발생한다() {
            //given
            String nullVmName = null;
            CreateVmRequest emptyVmNameRequest = new CreateVmRequest(nullVmName, originVmDescription, validVcpu, validMemory, validStorage, List.of(validNetworkId), null);

            //when & then
            Assertions.assertThatThrownBy(() -> vmService.createVmFrom(emptyVmNameRequest, saveMember)).isInstanceOf(ConstraintViolationException.class);
        }

        @Test
        void cpu_크기가_0이하면_예외가_발생한다() {
            //given
            int zeroVcpu = 0;
            CreateVmRequest zeroVcpuRequest = new CreateVmRequest(originVmName, originVmDescription, zeroVcpu, validMemory, validStorage, List.of(validNetworkId), null);

            //when & then
            Assertions.assertThatThrownBy(() -> vmService.createVmFrom(zeroVcpuRequest, saveMember)).isInstanceOf(ConstraintViolationException.class);
        }

        @Test
        void memory_크기가_0이하면_예외가_발생한다() {
            //given
            int zeroMemory = 0;
            CreateVmRequest zeroMemoryRequest = new CreateVmRequest(originVmName, originVmDescription, validVcpu, zeroMemory, validStorage, List.of(validNetworkId), null);

            //when & then
            Assertions.assertThatThrownBy(() -> vmService.createVmFrom(zeroMemoryRequest, saveMember)).isInstanceOf(ConstraintViolationException.class);
        }

        @Test
        void storage_크기가_0이하면_예외가_발생한다() {
            //given
            int zeroStorage = 0;
            CreateVmRequest zeroStorageRequest = new CreateVmRequest(originVmName, originVmDescription, validVcpu, validMemory, zeroStorage, List.of(validNetworkId), null);

            //when & then
            Assertions.assertThatThrownBy(() -> vmService.createVmFrom(zeroStorageRequest, saveMember)).isInstanceOf(ConstraintViolationException.class);
        }

        @Test
        void 올바른_입력값이_들어왔을경우_생성에_성공한다() {
            //given
            CreateVmRequest validVmRequest = new CreateVmRequest(originVmName, originVmDescription, validVcpu, validMemory, validStorage, List.of(validNetworkId), null);

            //when
            int createVmId = vmService.createVmFrom(validVmRequest, saveMember).getVmId();
            em.flush();
            em.clear();

            //then
            Assertions.assertThat(vmRepository.findById(createVmId)).isPresent();
        }
    }

}