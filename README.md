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