# F5Hell
더 이상의 F5 지옥은 없다. 한정판 굿즈를 위한 완벽한 실시간 예매 솔루션


## 🛠 Tech Stack
- Java 17
- Spring Boot 3.2
- Redis
- Thymeleaf

## 📂 Project Structure
```text
src/main/java/com/codebyjoey/f5hell/
├── 🎮 controller/   # [C] 사용자 요청(URL) 처리 및 Thymeleaf 뷰 연결
├── ⚙️ service/      # [Business Logic] 대기열 순번 계산, 티켓팅 검증 등 핵심 로직
├── 🗄️ repository/   # [DB/Redis Access] RedisTemplate을 이용한 데이터 저장 및 조회
├── 📦 domain/        # [M] 데이터 구조 및 객체 정의
│   ├── entity/      # 데이터베이스 테이블과 매핑되는 객체
│   └── dto/         # 계층 간 데이터 전달용 객체 (Request/Response)
├── 🛠️ config/       # Redis, Security 등 시스템 환경 설정
└── 🛠️ common/       # 공통 예외 처리(Exception Handler), 유틸리티 클래스
```

## 📃Git Strategy: Git Flow
```text
Main:       # 배포 가능한 상태의 안정적인 코드 관리 (v1.0.0 등 태깅)
Develop:    # 기능 통합 및 차기 버전 개발 베이스
Feature:    # 단위 기능 구현 (feature/*)
Release:    # 배포 전 최종 QA 및 버전 확정
Hotfix:     # 운영 환경 긴급 버그 수정
```

##  🤝Naming Convention (Service Layer)

프로젝트의 유지보수와 일관성을 위해 서비스 계층의 메서드 명칭을 아래와 같이 표준화하여 사용합니다.

| 행위 | 메서드 명칭 | 설명                            |
| :--- | :--- |:------------------------------|
| **등록** | `create` | 새로운 리소스를 생성하고 영속화합니다.         |
| **수정** | `update` | 기존 리소스의 정보를 변경합니다.            |
| **삭제** | `delete` | 리소스를 시스템에서 제거합니다.             |
| **단건 조회** | `get` | 식별자(ID)를 통해 특정 리소스를 상세 조회합니다. |
| **목록 조회** | `getList` | 조건에 맞는 리소스 목록을 반환합니다.         |

### 🔗 URL Mapping (Controller)

화면(Thymeleaf) 중심의 설계를 따르며, 행위를 명시적으로 URL에 포함합니다.

- **조회:** `/domain`, `/domain/get/{id}`
- **등록:** `/domain/create` (GET/POST)
- **수정:** `/domain/update/{id}` (GET/POST)
- **삭제:** `/domain/delete/{id}` (POST)