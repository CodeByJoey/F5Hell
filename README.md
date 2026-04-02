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
## ✅ 검증 전략 (Validation Strategy)

본 프로젝트는 데이터의 무결성과 시스템의 안정성을 위해 검증 로직을 세 단계로 계층화하여 관리합니다. 이를 통해 각 레이어의 책임을 명확히 분리합니다.

### 검증 계층 구조

| 계층 (Layer) | 역할 (Responsibility) | 주요 검증 내용                              | 사용 기술/도구                                             |
| :--- | :--- |:--------------------------------------|:-----------------------------------------------------|
| **형식 검증**<br>(Presentation) | **Controller / DTO** | 데이터 형식, 필수 값 누락, 단순 범위(음수 등) 체크       | `Bean Validation`<br>(@Valid, @Min, @NotBlank 등)     |
| **비즈니스 검증**<br>(Domain) | **Service / Entity** | 시스템 상태에 따른 로직 검증 (중복 체크, 재고 확인, 권한 등) | `Custom Exception`<br>(Business Exception)           |
| **데이터 무결성**<br>(Persistence) | **Database** | 최종적인 데이터 정합성 보장                       | `DB Constraint`<br>(Unique, Foreign Key, Not Null 등) |

## 📊 로그 운영 및 레벨 관리 전략 (Logging Strategy)

프로젝트의 안정적인 운영과 빠른 트러블슈팅을 위해 다음과 같은 로그 레벨 관리 기준을 준수합니다. 모든 로그에는 `MDC`를 통해 발급된 고유한 `traceId`가 포함됩니다.

| 레벨 (Level) | 관리 기준 및 출력 시점 | 비즈니스 예시 (F5Hell) |
| :--- | :--- | :--- |
| **FATAL/ERROR** | 시스템 운영이 중단되거나 즉시 조치가 필요한 장애 | DB 연결 실패, 외부 API 연동 불가능, 심각한 시스템 오류 |
| **WARN** | 당장 장애는 아니지만, 잠재적 위험이 있는 상태 | 10,000건 데이터 처리 중 일부 누락, 권한 없는 사용자의 접근 시도 |
| **INFO** | **(운영 기본)** 주요 비즈니스 흐름 및 상태 변화 | 애플리케이션 기동/종료, 사용자 로그인, 대량 데이터 처리 시작/완료 |
| **DEBUG** | **(개발 기본)** 개발 단계에서 흐름 추적 및 파라미터 확인 | SQL 실행 쿼리 및 파라미터 바인딩, API 요청/응답 전문(Payload) |
| **TRACE** | 가장 상세한 정보 (라이브러리 내부 동작 등) | 프로젝트 내에서는 가급적 지양 (로그 폭집 방지) |