KNoT Android library
===

Please READ!!!
===

***

## Slack channel
https://cesar.slack.com/messages/knot-iot-android

## Jira
https://jira.cesar.org.br/browse/KNOT/component/11717

***
## Jenkins
Coming soon

***
## Android Studio Settings

https://drive.google.com/open?id=0B6p4dYFlCDYRR091MTNSSGcxbFk&authuser=0

***

## Dev Workflow

1. Changes MUST have a JIRA ticket associated.
2. Branching naming convetion:
    * Features: feature/[JIRA-ISSUE] ex: feature/ISSUE-192
    * Bugfix: bugfix/[JIRA-ISSUE] ex: bugfix/ISSUE-193
3. Comment your commit:
    * Template: [JIRA-ISSUE]: Description ex: [ISSUE-192]: Add a new call to api.
4. Merge Requests are MANDATORY! No code SHALL PASS without MRs!!!

***

## Using Android library

1. Add maven repo to gradle:
>
```
   allprojects {
        repositories {
            maven {
                url "https://gitlab.cesar.org.br/cesar-iot/knot-android-lib-source/raw/mvn-repo"
            }
        }
    }
```
>

2. Add the dependency to app gradle:
>
```
    compile 'br.org.cesar:knot-library:1.0.0'
```
>