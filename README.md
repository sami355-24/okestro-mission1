# 🔥 개요
> 해당 미션의 목표는 API 설계 및 구현에 초점을 맞췄습니다.
> 소재가 가상머신이긴 합니다만, 실제 가상머신을 생성하기 위한 오픈스텍 컨트롤은 본 미션에 포함하지 않을 계획입니다.

# 🏁 Qucik Start
※ docker, ide가 있어야 합니다.<br>
1️⃣ 개인 ide에서 레포지토리를 clone합니다.
```
gh repo clone sami355-24/okestro-mission1
```
2️⃣ clone한 경로에서 다음과 같은 스크립트를 수행합니다.
```
./up-container.sh
```


# ⚒️ 아키텍쳐
![image](https://github.com/user-attachments/assets/87860436-27e4-47b4-b4da-f46002ab45ba)


# 📝 커밋 컨벤션
### 메시지 구조


```
[type(옵션)]: [#issueNumber] subject     //  -> 제목

body(옵션)     //  -> 본문
```

- `type`
    - 해당 커밋의 의도를 명시하는 부분
- `subject`
    - 50자 미만으로 제한
    - 영문 표기의 경우 동사(원형)을 맨 앞에 두고 첫 글자는 대문자로 표기
- `body`
    - 긴 설명이 필요할 경우 작성
    - 한 줄 당 75자 미만으로 제한
    - **어떻게** 했는지보다, **무엇을 왜** 했는지에 대하여 작성
    - 양에 구애받지 않고 최대한 상세하게 작성

### 타입 작성법

---

타입은 태그와 제목으로 구성되며, 태그는 영어로 작성하되 첫 글자는 대문자로 함

| TYPE                | 설정                                   | **구분** |
|---------------------|--------------------------------------|--------|
| `Feat`              | 새로운 기능 추가                            | **기능** |
| `Fix`               | 버그 수정                                | **기능** |
| `Style`             | 코드 포맷팅 (코드에 논리적 변경사항이 없는 경우)         | **개선** |
| `Refactor`          | 코드 리팩토링. 새로운 기능이나 버그 수정 없이 현재 구현을 개선 | **개선** |
| `Comment`           | 필요한 주석 추가 및 변경                       | **개선** |
| `Establish`         | 초기 환경설정 / 설정 파일 추가                   | 기타     |
| `Docs`              | 문서 수정                                | 기타     |
| `Test`              | 테스트 코드 추가                            | 기타     |
| `Remove` / `Rename` | 파일 삭제 / 파일명 변경                       | 기타     |
| `!HOTFIX`           | 긴급한 버그 수정 (핫픽스)                      | 기타     |
| `Chore`             | 기타                                   | 기타     |

# 🔗 Reference

- [jira](https://okestro.atlassian.net/browse/OKCPPP-779)
- [confluence](https://okestro.atlassian.net/wiki/spaces/CBSPPP2411/pages/1852145836/Tech+Stack)
