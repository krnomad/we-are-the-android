
# android-platform-tools
- https://developer.android.com/studio/releases/platform-tools

# LogFilter
- https://injunech.tistory.com/227

# 안드로이드 4대 component
- `Activity`, `Service`, `Broadcast receiver`, `Content Provider`
- component == process
    * Activity => process => app
    * Service => background service
        * fourgrand/background
    * Broadcast receiver
    * Content Provider => Database

- Activity 2개 -> process (1개 or 2개)
    - Comoponent이기 때문에 Component(ICP통신)
        - **Intent** -> 컴포넌트간 통신할 때 사용하는 interface

- Wifi list 확인

# Wifi scan sample app
- https://github.com/bewue/WLANScanner.git

- 처리과정
    - Send scan request event -> Android framework -> Scan
    - Scan requeslt (broadcast) -> Broadcast receiver(scan result) -> handleEvent

# Link
- https://github.com/krnomad/we-are-the-android.git