# StroyProjectX

Для запуска:

1) скачать gradle ( http://gradle.org/ )

2) скачать Java IDE (рекомендуемая - Intellij Idea, в дальнейшем все инструкции для нее)

3) File -> New -> Project from existing sources -> {путь} -> Gradle

4) View -> Tool Windows -> Gradle -> Refresh All Gradle Projects (первая кнопка на панели, 'обновить')

5) зайти в директорию проекта /resources/resources/lib и выполнить update bower для установки зависимостей 

6) запустить src/main/java/com/spx/config/Application.java (правая кнопка мыши -> run Application.main())

7) дождаться конца логов и открыть localhost:8080 , user/user

------------------------

Для обновления frontend-файлов:

1) скачать https://www.dropbox.com/s/9t0c314fymjec1c/update.bat?dl=0

2) открыть файл с помощью блокнота и изменить пути на свои (их там 2)

3) в IDE Edit Configurations (правый верхний угол, где название запускаемого класса)

4) + ->Gradle (Name сделать Update), раздел Before launch + run external tool -> update.bat -> Apply

5) теперь в случае обновления фронтенда не надо перезагружать сервер, достаточно вызвать Update

------------------------
Работа с Bower

1)для установки и работы bower нужен npm, выполняем curl https://npmjs.org/install.sh | sh

2)для установки bower выполняем npm install -g bower

3)чтобы подтянуть все зависимости из нашего bower.json (расположен /resources/resources/lib) выполняем bower update

4)для установки какого либо пакета, либо добавляем его в bower.json и затем выполняем bower update, либо выполняем bower install <package_name> --save



