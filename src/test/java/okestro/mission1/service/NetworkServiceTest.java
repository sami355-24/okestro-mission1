package okestro.mission1.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import okestro.mission1.entity.Member;
import okestro.mission1.entity.Network;
import okestro.mission1.entity.Vm;
import okestro.mission1.entity.VmStatus;
import okestro.mission1.exception.NotExistException;
import okestro.mission1.repository.MemberRepository;
import okestro.mission1.repository.NetworkRepository;
import okestro.mission1.repository.VmRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class NetworkServiceTest {

    @Autowired
    NetworkService networkService;

    @Autowired
    NetworkRepository networkRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    VmRepository vmRepository;

    @PersistenceContext
    EntityManager em;

    int validNetworkId;

    @BeforeEach
    void setUp() {

        Member saveMember = Member.builder()
                .email("test@email.com")
                .password("1q2w3e4R!")
                .build();

        Network saveNetwork = Network.builder()
                .name("Test Network")
                .openIp("1.1.1.2")
                .openPort(10000).build();


        memberRepository.save(saveMember);
        networkRepository.save(saveNetwork);

        vmRepository.save(Vm.builder()
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
                .build());

        validNetworkId = saveNetwork.getNetworkId();

        em.flush();
        em.clear();
    }

    @Nested
    class 네트워크_Id_검즘을_시도할_때 {
        @Test
        void 네트워크_id가_DB에_존재하지_않는다면_예외가_발생한다() {
            //given
            int notExistingNetworkId = -1;

            //when & then
            assertThatThrownBy(()-> networkService.validateNetworkId(List.of(notExistingNetworkId))).isInstanceOf(NotExistException.class);
        }

        @Test
        void 하나의_네트워크_id라도_DB에_존재하지_않는다면_예외가_발생한다() {
            //given
            int notExistingNetworkId = -1;

            //when & then
            assertThatThrownBy(()-> networkService.validateNetworkId(List.of(validNetworkId, notExistingNetworkId))).isInstanceOf(NotExistException.class);
        }

        @Test
        void 네트워크_id가_DB에_존재한다면_검증에_성공한다(){
            //when & then
            assertThatCode(()-> networkService.validateNetworkId(List.of(validNetworkId))).doesNotThrowAnyException();
        }
    }

}