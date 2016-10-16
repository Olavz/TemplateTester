# TemplateTester

What is TemplateTester?

TemplateTester is intended to test multiple pages that are similar to each other, 
but where we cant be certain of the actual values. Thus achieving a way of doing template testing.

## Configuring the project

### Building TestCriteria
...

### Use TestPageProvider to configure source

The current implementation allows the test pages to be provided from an external source.

The TestPageProvider.getTestPagesFromUrl(String url) builds a list of TestPage objects where the provided URL produces the following JSON data:
```
{
	randomUrls: [
		{
			name: "Page: 47617",
			url: "http://www.example.com/47617/"
		},
		{
			name: "Page: 57925",
			url: "http://www.exampleccom/57925/"
		}
	]
}
```

## Configure for Jenkins

Jenkins should be configured to run the project on a scheduled interval. The project aims to check HTML pages and not checked in code. 

### How to enable?

Install the Html Publisher Plugin if not already installed. This enables a neat HTML presentation within Jenkins.
https://wiki.jenkins-ci.org/display/JENKINS/HTML+Publisher+Plugin

#### Example Jenkins 2.0 - Pipeline

```
node {
    stage('Run tests') {
        sh "mvn -Dmaven.test.failure.ignore clean test"
    }
    stage('Publish Test results') {
        publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: true, reportDir: 'target/test-classes', reportFiles: 'template-test-report.html', reportName: 'Test results'])
    }
}
```

#### Disable Security restrictions

Optionally the Bootstrap framework and jQuery files could be stored along with the reports, 
but since we want to archive the reports for each build, one would end up storing the Bootstrap 
framework along with the test-report each time. No need to clutter up storage on copies of Bootstrap and jQuery.

Navigate over to localhost:8080/jenkins/script and run the following snippet. Or go through the menu: `Manage Jenkins -> Script Console`.

``System.setProperty("hudson.model.DirectoryBrowserSupport.CSP", " default-src 'unsafe-inline' *;")``

This will allow the published HTML report to load external resources such as jQuery and Bootstrap from CDN resources.

More reading on how to configure Jenkins Security Policy: https://wiki.jenkins-ci.org/display/JENKINS/Configuring+Content+Security+Policy