#UpMobSDK для сайта upmob.ru
Это SDK позволяет мотвировать исполнителей за любые действия. Например за пройденный уровень, за регистрацию в приложении и т.д.

Add this in your root build.gradle file (not your module build.gradle file):
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
}
```

Then, add the library to your module build.gradle
```
dependencies {
    implementation 'com.github.bumsun:upmob_sdk_events:v1.8'
}
```

Код автоматически будет генерироваться после создания заказа, но для большей ясности здесь тоже опишем.

```
# Инициализация
# Вызвать при запуске приложения
UpMob.init(getApplicationContext());
```

```
# Вызвать в момент когда задача выполнена
UpMob.sendEvent("task_id");
```