# UpMobSDK для сайта upmob.ru
Это SDK позволяет мотивировать исполнителей за любые действия. Например за пройденный уровень, за регистрацию в приложении и т.д.
Код автоматически будет генерироваться после создания заказа у нас на сайте, но для большей ясности здесь тоже опишем.

### Зависимости
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
    implementation "com.android.installreferrer:installreferrer:2.2"
    implementation 'com.github.bumsun:upmob_sdk_events:v2.1'
}
```

### Инициализация

```
# Вызвать в методе onCreate главной Activity (Которая запускается первой в приложении)
UpMob.init(getApplicationContext());
```

### Вызвать в момент когда задача выполнена
```
UpMob.sendEvent("task_id");
```
