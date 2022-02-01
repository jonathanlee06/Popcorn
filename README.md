<h1>Popcorn - Movie App 🎬</h1>

<p>
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=23"><img alt="API" src="https://img.shields.io/badge/API-23%2B-brightgreen.svg?style=flat"/></a>
</p>

Popcorn is a simple movie Android application built
by [Jonathan Lee](https://github.com/jonathanlee06)
utilizing [The Movie DB](https://www.themoviedb.org/) API, based on Kotlin MVP architecture and
material designs & animations

> Made with :heart: in Malaysia

<p align="center">
<img src="/previews/preview.jpg" />
</p>

## :page_with_curl: Note

The purpose of this project is to keep myself up-to-date with the latest tech stacks & learn the
best practices used in Android development. Currently this project is still in development phase,
and more features will be added in the future.

## :hammer: Building the project

You can view the source code of the project using Android Studio and build it yourself by following
these steps:

1. Clone this project to your desired location
2. Open the project folder using Android Studio
3. Add your [The Movie DB](https://www.themoviedb.org/)'s API key in `local.properties` file.

```xml
tmdb_api_key=YOUR_API_KEY
```

## :books: Tech stack & Libraries

- Minimum SDK level 23
- 100% [Kotlin](https://kotlinlang.org/) based
  + [Coroutines](https://github.com/Kotlin/kotlinx.coroutines)
- Architecture
  - MVP Architecture (Model - View - Presenter)
  - Repository pattern
  - ViewBinding
- Material Design & Animations
- [Retrofit2](https://github.com/square/retrofit) & [Gson](https://github.com/google/gson) for
  building the REST API calls
- [OkHttp3](https://github.com/square/okhttp) for building request interceptor
- [Coil](https://github.com/coil-kt/coil) for image loading
- [Firebase Crashlytics](https://firebase.google.com/docs/crashlytics/) for online crash monitoring

## :heart: See anything you like?

Support it by joining __[stargazers](https://github.com/jonathanlee06/Popcorn/stargazers)__ for this
repository. :star:

## :octocat: Author

[Jonathan Lee](https://github.com/jonathanlee06)

## :bookmark_tabs: License

This project is licensed under the Apache License, Version 2.0 . See
the [LICENSE](https://github.com/jonathanlee06/Popcorn/blob/master/LICENSE) file for more info.

```xml
Designed and developed by 2022 Jonathan Lee

        Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

