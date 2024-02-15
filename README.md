![](https://github.com/PamCatten/hot-stuff/blob/main/assets/banner.png)
![Static Badge](https://img.shields.io/badge/Release-1.0.0-%23F83839)
![Static Badge](https://img.shields.io/badge/API-33%2B-%2340B5F0)
[![License](https://img.shields.io/badge/License-Apache%202.0-%23FFA303)](http://www.apache.org/licenses/LICENSE-2.0.html)

Hot Stuff is an offline Android app designed to help users quickly record and aggregate belongings for Proof of Loss forms in insurance claims. 
// TODO: Add more, but not so much that it overwhelms

## Getting Started
// TODO: Installation steps, links to Google Play and anywhere else HS is available on

#### Supported Versions
Currently, this app supports a minimum Android SDK version of **33**, and the target SDK version is **34**.

## Documentation
See the documentation for descriptions and usage examples of Hot Stuff's functions.

## Design
### Design Document
See the [technical design document](https://docs.google.com/document/d/177xGpi3BK14RevmSoKWCA5AEw8B0tt4f7ILGGo3Y3MI/edit#heading=h.j040vupqoagj) for more details about how or why Hot Stuff is in its current state. 
### Design Philosophy
Filing an insurance claim is a difficult process, especially in the wake of fires or other devastating emergencies; Hot Stuff was built with this in mind.
It's designed to provide a simple, accessible solution for managing belongings, and to streamline the cataloguing process without overwhelming users with unneded complexity. 

The underlying purpose of Hot Stuff is to serve as a bridge between traditional methods and the spreadsheet-based inventory management software preferred by insurance providers. While spreadsheets can be convenient for people who are familiar with computers, we know that some people find using these programs challenging, and would prefer to use applications on their mobile device instead. Hot Stuff exists to serve these needs and to provide a middle ground between methods by offering an easy-to-use interface for users to work with on their mobile devices, that can then, with permission, convert their information to work within spreadsheet-based software if users wish to transition formats.

Recognizing the importance of accessibility, Hot Stuff is built to operate entirely offline. This means that users can manage their inventory data regardless of internet connectivity, making it a reliable solution for remote or unstable network environments. It also means that users have complete privacy and security, because their sensitive inventory information never leaves their device. 

However, it's essential to acknowledge the inherent trade-off associated with local storage: users bear the responsibility of their data, increasing the risk of irreversible data loss by app deletion or device destruction as time goes on. Therefore, Hot Stuff has a built-in shelf-life. While it serves as a valuable tool for recording and organizing belongings, its ultimate purpose is to be a helpful, unobstrusive, and easy-to-use middleman that helps users transition to a better, more permanent storage solution. 

We *strongly* suggest the following lifecycle for Hot Stuff usage. Users should: 
* Download the application
* Familiarize themselves with the inventory process and their insurance requirements
* Record their belongings at their convenience and to their best satisfaction
* Export their inventory information to a spreadsheet for long-term maintenance and storage
* Tearfully wave goodbye and delete the application

By embracing this lifecycle, users will both mitigate the risks associated with extended reliance on the app, and help ensure the long-term safety of their inventory data.

When designing Hot Stuff, our hope was to create a companion for our users that could both help safeguard peace of mind in the day-to-day, or help ease the burden that comes in the aftermath of tragedy. If we succeeded, and you have the time to do so, consider getting involved; If we failed, consider giving us some [feedback](https://github.com/PamCatten/hot-stuff/issues/new?assignees=&labels=enhancement&projects=&template=feature.md&title=%5BFEATURE%5D): we've come a long way already, but there's always more we can do.
## Accessibility
// TODO: Extremely important, include references to all work done for this objective
### Screen Reader Support
### CVD (Color Vision Deficiency) Support
To ensure that text and other important components are easily distinguishable for users with CVD, we have made an effort to optimize color contrast where possible. 


For example: the color palette used to plot chart elements for charts were chosen to be distinguishable for users with various types of CVD, below are some visualizations to help demonstrate.

Here is the palette with 'normal' vision: ![](https://github.com/PamCatten/hot-stuff/blob/main/assets/theme_normal.png)

Here is the palette with deuteranopic (red-green) vision: ![](https://github.com/PamCatten/hot-stuff/blob/main/assets/theme_deuternanopia.png)

Here is the palette with protanopic (red-green) vision: ![](https://github.com/PamCatten/hot-stuff/blob/main/assets/theme_protanopia.png)

Here is the palette with tritanopic (blue-yellow) vision: ![](https://github.com/PamCatten/hot-stuff/blob/main/assets/theme_tritanopia.png)

As you can see, despite sharing the same hue, the colors in the palette closest in shade and staturation should be distinctive enough for users with various types of CVD to differentiate between them.

### Right-to-Left Text Support
### Additional Language Support

## Similar software
The format of Hot Stuff is entirely our own creation, which is unfortunate, because it means we put a lot time into re-inventing stuff that already exists! If, like us, you didn't know the name of this type of project, and as a result, didn't know what to google when you started, these apps are called home inventory or inventory management software. 

Due to its offline nature, FOSS-ness, requiring no account or personal information, and a (relatively) lighter size, Hot Stuff still serves a niche unfilled by some of the other software listed below; with the caveat that the general structure will likely be similar to some, if not all of the other applications.  

Similar deployed software that helps users record, sort, and list their items on Android (the list may be incomplete, and some appear to be unmaintained):
* [Sortly](https://play.google.com/store/apps/details?id=com.sortly.mythings&pcampaignid=web_share)
* [Encircle](https://play.google.com/store/apps/details?id=com.encircle&pcampaignid=web_share)
* [HouseBook](https://play.google.com/store/apps/details?id=chenige.chkchk.wairz&pcampaignid=web_share)
* [Smart Inventory](https://play.google.com/store/apps/details?id=com.nonzeroapps.android.smartinventory&pcampaignid=web_share)
* [ZenOwn](https://play.google.com/store/apps/details?id=com.zenown.app&hl=en&gl=US)
* [My Stuff Organizer](https://play.google.com/store/apps/details?id=com.ebizzapps.mystufforganizer&hl=en&gl=US)

## Contributing
Bug reports, feature requests, questions, and pull requests always welcome! 

Check out our [Contributing Guidelines](https://github.com/PamCatten/hot-stuff/blob/main/CONTRIBUTING.md).
## Legal
#### Developers
This project is FOSS (Free and Open Source Software) licensed under the Apache License, Version 2.0. You can read the license in its entirety either [here](https://github.com/PamCatten/hot-stuff/blob/main/LICENSE) or [here](https://www.apache.org/licenses/LICENSE-2.0).
#### Users
This app is subject to our [Terms of Use](https://github.com/PamCatten/hot-stuff/blob/main/TERMS).


## Contact
For any inquiries or feedback, feel free to start a [discussion](https://github.com/PamCatten/hot-stuff/discussions), create an [issue](https://github.com/PamCatten/hot-stuff/issues), or reach out over [email](mailto:campatten.dev@outlook.com).
