# Contributing Guidelines

The biggest ongoing priority with Hot Stuff is continuing to improve accessibility, including keeping it as easy as possible for contributers to 
make the features, bug-fixes, and other changes that will continue to improve user experience all around the world, without sacrificing code quality. 

That being said, code quality remains an ever-present issue in keeping projects maintainable, so please follow the contributer guidelines so 
that Hot Stuff stays organized.

## Submitting issues
As stated before, bug reports, feature requests, and questions are all welcome. Submit issues [here](https://github.com/PamCatten/hot-stuff-kotlin/issues).

There are two main issue templates, one for **bug reports** and another for **feature requests**. Please use them! You're issue will be much easier to understand, and bugs 
easier to fix, if you follow the templates. If your issue doesn't fit into those, no worries, just use the generic template.

When submitting a bug report:
* Search through existing issues, in case someone has already reported your bug. If so, comment the details of your bug to provide more context! 
* Test the bug against the most recently release version of Hot Stuff. Who knows, it may have been fixed already!
* Include all the details the template asks for if possible

When submitting a feature request:
* Search through existing issues, in case someone else may have had the same idea. If so, comment to show your support!
* Briefly explain why you think Hot Stuff be improved by this feature
* Include all the details the template asks for if possible

## Submitting PRs
These guidelines exist to make life easy for all of us, so please try your best to follow them, I promise you 
that this process is much more fun when things can move along efficiently. 
0. Search open pull requests AND existing issues to make sure the problem/feature you're solving/adding isn't already being worked on or has an open pull request
1. Fork the [repo](https://github.com/PamCatten/hot-stuff-kotlin)
2. Create a new branch based on `main`, and name it something related to your changes
3. Add your commits, following the [Commit Style Guide](#commit-style-guide)
4. Write test coverage for your changes
5. Create the pull request, and resolve any identified issues
6. Wait for merge approval
7. Celebrate!

## Commit Style Guide
Hot Stuff is a baby. We're a young project with some room to grow, so we're easy, 
we're breezy, dare I say we're downright flexible when it comes to 
commits. Like all things in life however, this could be subject to change, 
so try to check this section every once and a while if you're gonna stick around.

#### Logical divisions of work
Try not to fill a single commit with a ton of changes, because that can make impact hard to track. If 
possible, divide the work into sections, and commit accordingly as you finish them;
however, if you're doing a lot of work, and the pile of commits and merges is starting to get pretty tall, 
absolutely feel free to squash them down into bigger commits for everyone's sanity.

#### Code style
Try to follow the [Kotlin Coding Conventions](https://kotlinlang.org/docs/reference/coding-conventions.html).

#### Commit messages
Try to follow the more or less commonly accepted format that follows (lifted from this relatively famous 
[blog post](https://tbaggery.com/2008/04/19/a-note-about-git-commit-messages.html)):
* Short (50 chars or less) summary of changes
* (Blank line)
* More detailed explanatory text, if necessary. Wrapped to 72 (or 100) characters or so. In some contexts, the
  first line is treated as the subject of an email and the rest of the text as the body.  The blank line
  separating the summary from the body is critical (unless you omit the body entirely); tools like rebase can
  get confused if you run the two together
* (Blank line)
* Further paragraphs, if necessary

## Final Notes
If you're planning a big change, feel free to start a [discussion](https://github.com/PamCatten/hot-stuff-kotlin/discussions) or reach out over [email](mailto:campatten.dev@outlook.com), 
people may be able to help!

Hey! Thanks for taking the time to read Hot Stuff's contributing guidelines! You're welcome here any time.
