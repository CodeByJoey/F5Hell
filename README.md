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

##  로그 운영 및 레벨 관리 전략 (Logging Strategy)

프로젝트의 안정적인 운영과 빠른 트러블슈팅을 위해 다음과 같은 로그 레벨 관리 기준을 준수합니다. 모든 로그에는 `MDC`를 통해 발급된 고유한 `traceId`가 포함됩니다.

| 레벨 (Level) | 관리 기준 및 출력 시점 | 비즈니스 예시 (F5Hell) |
| :--- | :--- | :--- |
| **FATAL/ERROR** | 시스템 운영이 중단되거나 즉시 조치가 필요한 장애 | DB 연결 실패, 외부 API 연동 불가능, 심각한 시스템 오류 |
| **WARN** | 당장 장애는 아니지만, 잠재적 위험이 있는 상태 | 10,000건 데이터 처리 중 일부 누락, 권한 없는 사용자의 접근 시도 |
| **INFO** | **(운영 기본)** 주요 비즈니스 흐름 및 상태 변화 | 애플리케이션 기동/종료, 사용자 로그인, 대량 데이터 처리 시작/완료 |
| **DEBUG** | **(개발 기본)** 개발 단계에서 흐름 추적 및 파라미터 확인 | SQL 실행 쿼리 및 파라미터 바인딩, API 요청/응답 전문(Payload) |
| **TRACE** | 가장 상세한 정보 (라이브러리 내부 동작 등) | 프로젝트 내에서는 가급적 지양 (로그 폭집 방지) |