# Koshry


## What's Koshry?
**Koshry**:
- is a dev tool, runs during your **CI Build** or on your **Local Machine Build**.
- gives your team the ability to monitor **pull requests** and take automatic **actions** depend on your rules result.
- create a report of the result of applying your rules on the pull request and post it back as a comment on the pull request.
- is written in **Kotlin** and runs on JVM.
- has some of the ready rules for common use cases.
- is **customizable**, you can [write your own **Custom Rule**](https://medium.com/@Tarek360/your-first-koshry-rule-c6648ac34ca2), everything is a **Rule** in Koshry.
- works on any project whatever the programming language you use in your project


## Run Koshry in 3 steps:

**1-** Add Koshry to your build process:

Gradle:
```gradle
implementation "io.github.tarek360.koshry:koshry:0.0.4"
```

**2-** Invoke Koshry with one rule at least.

```kotlin
val koshryConfig = koshry {
    rules {
        rule = rule1
        rule = rule2
    }
}

Koshry.run(koshryConfig)
```

**3-** Add one environment variable to your CI.

Add your git host (Github for example) token as an environment variable to allow Koshry post a comment and update the commit status.
Use `KOSHRY_GIT_HOST_TOKEN` as a key of the environment variable.


## Koshry Rules:
Each Koshry **Rule** does some work then returns a **Report** (or not) as a result of that work.

While you can [create your own custom rule](https://medium.com/@Tarek360/your-first-koshry-rule-c6648ac34ca2), Koshry has some of the ready rules, you can make use of them directly.
These rules are applied to the **git diff** of the  **pull request**.
- **LineRule**: Apply a **condition** to all of the **added** and **modified lines** in the pull request.
- **FileRule**: Apply a **condition** to all of the **added** and **modified files** in the pull request.
- **ProtectedFilesRule**: Protect a list of files to be changed by someone hasn't permission to make a change.

## Koshry works automatically with:
- CI
    - [Travis CI](https://travis-ci.com)
    - [circle ci](https://circleci.com)
    - [TeamCity](https://www.jetbrains.com/teamcity)
    
- Git host:
  - Github
  
## Contribution
- Feel free to fork and open a pull request.
- Open an issue if you found a bug or you need help.
  

## How does Kosrhy work?
In a brief, when you call `Koshry.run(koshryConfig)`, Koshry applies all the rules you have set and it aggregates all of the reports returned from the rules to one report, then it posts that report as a comment on the pull request.

**Note**: Kosrhy applies all of the rules sequentially.

## I use Koshry in Koshry
I use Koshry here in this repository to apply some rules like **JaCoCo Test Coverage** rule to set the minimum percentage of test coverage of PRs, check Koshry report on one of my PRs [here](https://github.com/tarek360/koshry/pull/18)
