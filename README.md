# F5H
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
├── 📦 model/        # [M] 데이터 구조 및 객체 정의
│   ├── entity/      # 데이터베이스 테이블과 매핑되는 객체
│   └── dto/         # 계층 간 데이터 전달용 객체 (Request/Response)
├── 🛠️ config/       # Redis, Security 등 시스템 환경 설정
└── 🛠️ common/       # 공통 예외 처리(Exception Handler), 유틸리티 클래스
```

### 🚀 다음 단계: `develop` 브랜치에 기록하기

```bash
# 1. README.md 수정 후 저장
# 2. 스테이징 및 커밋 (develop 브랜치인지 꼭 확인하세요!)
git add README.md
git commit -m "docs: 프로젝트 디렉토리 구조 정의 및 README 업데이트"

# 3. 원격에 푸시
git push origin develop
```