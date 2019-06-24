# GitHubRepoFetch

This is the practical app that I had made for one of my technical interviews in the past.

Task requirements:

Check out GitHub public api v3. Make a lightweight app that fulfils the following requirements:

-	Displays the list of ALL public repositories
-	Results must be displayed page wise (25 results per page) and must contain the name and full name of the repo.
-	If the repo is selected, then further information will be displayed which must contain: size, stargazers, forks count and the list of the contributors of this repo. 
-	The list of contributors must contain the login name of the user as well as their avatar picture.
-	Make sure the app has a proper architecture and intuitive UI design.
-	Use of any third party frameworks is allowed (unless you want to complete all bonus points).

Bonus: 
-	Do not use any third party frameworks
-	Use Kotlin 


The problem that I had encountered in the development:
The problem that I had encountered is the URL ( https://api.github.com/repositories?since=0 ) that it will always return 100 objects despite the rest of the query parameters. I had also tried combinations of "per_page=25" and "page=1". But with no luck. So the question for me was "should I deliver a problematic app to be evaluated or should I send something us similar us possible?" In the end, I am displaying all the repositories that Google has on GitHub. Sorry for the inconvenience
