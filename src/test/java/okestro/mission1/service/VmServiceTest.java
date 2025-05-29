package okestro.mission1.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import okestro.mission1.dto.controller.request.CreateVmRequestDto;
import okestro.mission1.dto.controller.request.PageSize;
import okestro.mission1.dto.controller.request.UpdateVmRequestDto;
import okestro.mission1.dto.controller.response.FindFilterVmResponseDto;
import okestro.mission1.dto.repository.OrderParams;
import okestro.mission1.dto.service.vm.FindFilterVmServiceDto;
import okestro.mission1.dto.service.vm.UpdateVmServiceDto;
import okestro.mission1.entity.*;
import okestro.mission1.exception.custom.InvalidDataException;
import okestro.mission1.exception.custom.NotExistException;
import okestro.mission1.repository.MemberRepository;
import okestro.mission1.repository.NetworkRepository;
import okestro.mission1.repository.TagRepository;
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

    @Autowired
    private TagRepository tagRepository;

    @PersistenceContext
    EntityManager em;

    Member validMember;

    int validVmId;
    int validNetworkId;
    int validTagId;
    Vm validVm;
    Network validNetwork;
    Tag validTag;

    @BeforeEach
    void setUp() {
        tagRepository.deleteAll();
        memberRepository.deleteAll();
        networkRepository.deleteAll();
        vmRepository.deleteAll();

        this.validMember = Member.builder()
                .email("test@email.com")
                .password("1q2w3e4R!")
                .build();

        this.validNetwork = Network.builder()
                .name("Test Network")
                .openIp("1.1.1.2")
                .openPort(10000).build();

        memberRepository.save(validMember);
        networkRepository.save(validNetwork);
        tagRepository.saveAll(
                List.of(
                        Tag.builder().name("DEV").build(),
                        Tag.builder().name("TEST").build(),
                        Tag.builder().name("BUILD").build(),
                        Tag.builder().name("PROD").build(),
                        Tag.builder().name("EXISTING").build()
                )
        );
        List<Vm> savedVms = vmRepository.saveAll(
                List.of(
                        Vm.builder()
                                .member(validMember)
                                .vmStatus(VmStatus.STARTING)
                                .name("vm1")
                                .description("Test VM 1")
                                .vCpu(4)
                                .memory(16)
                                .storage(4)
                                .member(validMember)
                                .privateIp("1.1.1.1")
                                .networks(List.of(validNetwork))
                                .deleted(false)
                                .build(),
                        Vm.builder()
                                .member(validMember)
                                .vmStatus(VmStatus.RUNNING)
                                .name("vm2")
                                .description("Test VM 2")
                                .vCpu(4)
                                .memory(20)
                                .storage(8)
                                .member(validMember)
                                .privateIp("2.2.2.2")
                                .networks(List.of(validNetwork))
                                .deleted(false)
                                .build(),
                        Vm.builder()
                                .member(validMember)
                                .vmStatus(VmStatus.RUNNING)
                                .name("vm-duplicate")
                                .description("Test VM Duplicate")
                                .vCpu(4)
                                .memory(20)
                                .storage(8)
                                .member(validMember)
                                .privateIp("3.3.3.3")
                                .networks(List.of(validNetwork))
                                .deleted(false)
                                .build()
                )
        );

        em.flush();

        validVm = savedVms.get(0);
        validVmId = validVm.getVmId();
        validNetworkId = validNetwork.getNetworkId();
        validTag = tagRepository.findAll().get(0);
        validTagId = validTag.getId();

        em.clear();
    }

    @Nested
    class 가상머신_단일_조회_시도시 {
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
    class 가상머신_목록_조회_시도시 {

        @Nested
        class 태그id_목록이_주어지고 {
            int page = 1;

            @Test
            void DB에_존재하는_태그_id가_있다면_조회에_성공한다() {
                FindFilterVmServiceDto invalidFilterRequest = new FindFilterVmServiceDto(
                        page,
                        PageSize.FIVE,
                        List.of(),
                        OrderParams.NAME_DESC,
                        null,
                        null
                );

                //when
                FindFilterVmResponseDto findVms = vmService.findFilterVms(invalidFilterRequest);

                //then
                Assertions.assertThat(findVms.getPageContents()).isNotNull();
            }
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
            CreateVmRequestDto duplicateVmNameRequest = new CreateVmRequestDto(duplicateVmName, originVmDescription, validVcpu, validMemory, validStorage, List.of(validNetworkId), null);

            //when & then
            Assertions.assertThatThrownBy(() -> vmService.createVmFrom(duplicateVmNameRequest, validMember)).isInstanceOf(DataIntegrityViolationException.class);
        }

        @Test
        void 필수로_들어가야하는_데이터가_null일때_예외가_발생한다() {
            //given
            String nullVmName = null;
            CreateVmRequestDto emptyVmNameRequest = new CreateVmRequestDto(nullVmName, originVmDescription, validVcpu, validMemory, validStorage, List.of(validNetworkId), null);

            //when & then
            Assertions.assertThatThrownBy(() -> vmService.createVmFrom(emptyVmNameRequest, validMember)).isInstanceOf(ConstraintViolationException.class);
        }

        @Test
        void cpu_크기가_0이하면_예외가_발생한다() {
            //given
            int zeroVcpu = 0;
            CreateVmRequestDto zeroVcpuRequest = new CreateVmRequestDto(originVmName, originVmDescription, zeroVcpu, validMemory, validStorage, List.of(validNetworkId), null);

            //when & then
            Assertions.assertThatThrownBy(() -> vmService.createVmFrom(zeroVcpuRequest, validMember)).isInstanceOf(ConstraintViolationException.class);
        }

        @Test
        void memory_크기가_0이하면_예외가_발생한다() {
            //given
            int zeroMemory = 0;
            CreateVmRequestDto zeroMemoryRequest = new CreateVmRequestDto(originVmName, originVmDescription, validVcpu, zeroMemory, validStorage, List.of(validNetworkId), null);

            //when & then
            Assertions.assertThatThrownBy(() -> vmService.createVmFrom(zeroMemoryRequest, validMember)).isInstanceOf(ConstraintViolationException.class);
        }

        @Test
        void storage_크기가_0이하면_예외가_발생한다() {
            //given
            int zeroStorage = 0;
            CreateVmRequestDto zeroStorageRequest = new CreateVmRequestDto(originVmName, originVmDescription, validVcpu, validMemory, zeroStorage, List.of(validNetworkId), null);

            //when & then
            Assertions.assertThatThrownBy(() -> vmService.createVmFrom(zeroStorageRequest, validMember)).isInstanceOf(ConstraintViolationException.class);
        }

        @Test
        void 올바른_입력값이_들어왔을경우_생성에_성공한다() {
            //given
            CreateVmRequestDto validVmRequest = new CreateVmRequestDto(originVmName, originVmDescription, validVcpu, validMemory, validStorage, List.of(validNetworkId), null);

            //when
            int createVmId = vmService.createVmFrom(validVmRequest, validMember).getVmId();
            em.flush();
            em.clear();

            //then
            Assertions.assertThat(vmRepository.findById(createVmId)).isPresent();
        }
    }

    @Nested
    class 가상머신_수정_시도시 {
        final String validName = "new vm name";
        final String validDescription = "new vm description";
        final int validVcpu = 4;
        final int validMemory = 8;

        @Test
        void 존재하지_않는_가상머신_id로_수정시도할_경우_예외가_발생한다() {
            //given
            int invalidVmId = -1;
            UpdateVmRequestDto updateVmRequestDto = new UpdateVmRequestDto(validName, validDescription, validVcpu, validMemory, List.of(validNetworkId), List.of(validTagId));
            UpdateVmServiceDto updateVmServiceDto = new UpdateVmServiceDto(invalidVmId, List.of(validTag), List.of(validNetwork), updateVmRequestDto);

            //when & then
            assertThatThrownBy(() -> vmService.updateVm(updateVmServiceDto)).isInstanceOf(NotExistException.class);
        }

        @Test
        void 입력_값이_올바를경우_수정에_성공한다() {
            //given
            String updateVmName = "new vm name";
            String updateVmDescription = "new vm description";
            int updateVcpu = 11;
            int updateMemory = 22;
            UpdateVmRequestDto updateVmRequestDto = new UpdateVmRequestDto(updateVmName, updateVmDescription, updateVcpu, updateMemory, List.of(validNetworkId), List.of(validTagId));
            UpdateVmServiceDto updateVmServiceDto = new UpdateVmServiceDto(validVmId, List.of(validTag), List.of(validNetwork), updateVmRequestDto);

            //when
            vmService.updateVm(updateVmServiceDto);
            em.flush();

            //then
            Vm vm = vmRepository.findById(validVmId).orElseThrow(() -> new InvalidDataException("VM 데이터(vmId) 이상 존재"));
            org.junit.jupiter.api.Assertions.assertAll(
                    () -> assertThat(vm.getName()).isEqualTo(updateVmName),
                    () -> assertThat(vm.getDescription()).isEqualTo(updateVmDescription),
                    () -> assertThat(vm.getVCpu()).isEqualTo(updateVcpu),
                    () -> assertThat(vm.getMemory()).isEqualTo(updateMemory),
                    () -> assertThat(vm.getNetworks()
                            .stream()
                            .findFirst()
                            .orElseThrow(() -> new InvalidDataException("VM 데이터(network) 이상 존재"))
                            .getNetworkId()).isEqualTo(validNetworkId)
            );
        }

    }

    @Nested
    class 가상머신_삭제_시도시 {
        @Test
        void 가상머신_id가_db에_존재하지_않는다면_예외가_발생한다() {
            //given
            int notExistingVmId = -1;

            //when & then
            Assertions.assertThatThrownBy(() -> vmService.deleteVmFrom(notExistingVmId)).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 가상머신_id가_db에_존재한다면_삭제에_성공한다() {
            //when
            vmService.deleteVmFrom(validVmId);
            em.flush();
            List<Vm> deletedVm = vmRepository.findDeletedVmsWithNativeQuery();

            //then
            Assertions.assertThat(deletedVm.get(0).getVmId()).isEqualTo(validVmId);
        }
    }

}