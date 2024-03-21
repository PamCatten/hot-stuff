![](https://github.com/PamCatten/hot-stuff/blob/main/assets/banner.png)
![Static Badge](https://img.shields.io/badge/Release-1.0.0-%23F83839)
[![Build CI](https://github.com/PamCatten/hot-stuff/actions/workflows/build.yml/badge.svg?branch=main)](https://github.com/PamCatten/hot-stuff/actions/workflows/build.yml)
![Static Badge](https://img.shields.io/badge/API-33%2B-%2340B5F0)
[![License](https://img.shields.io/badge/License-Apache%202.0-%23FFA303)](http://www.apache.org/licenses/LICENSE-2.0.html)

An offline Android app designed to help users quickly and accurately document belongings for proof of loss forms in insurance claims. 

## Getting Started
// TODO: Links to Google Play and anywhere else HS is available on

#### Local Install Prerequisites
To get started with this project, ensure you have the following:

* Android Studio | [Download Android Studio](https://developer.android.com/studio)
* Android Software Development Kit (SDK) | [Set up Android 13 SDK](https://developer.android.com/about/versions/13/setup-sdk) 
* Java Development Kit (JDK) | [Download Java 17 JDK](https://www.oracle.com/java/technologies/downloads/#java17)

Installation steps:
```
Clone the repository to your local machine:
git clone https://github.com/PamCatten/hot-stuff.git
```
1. Clone the project
2. Open the project in Android Studio
3. Build and run the project on an emulator or a physical device running at least Android 13, API level 33
   
## Documentation
See the documentation for descriptions and usage examples of Hot Stuff's functions.

## Contributing
Bug reports, feature requests, questions, and pull requests always welcome! 

Check out our [Contributing Guidelines](https://github.com/PamCatten/hot-stuff/blob/main/CONTRIBUTING.md).
## Testing
View the latest test coverage status report on [Codecov](https://app.codecov.io/github/PamCatten/hot-stuff).

## Design
### Roadmap
See the [roadmap](https://github.com/PamCatten/hot-stuff/blob/main/ROADMAP.md) for more details about what we plan to include in future releases.
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
We are committed to ensuring accessibility for all users and as such have taken steps with the design of the application to be in compliance with the Web Content Accessibility Guidelines (WCAG) 2.2 Level A standards. You can read more about the standards on the W3C's [website](https://www.w3.org/TR/WCAG22/).

### Visual Impairment (VI) Support
All non-text content within the application, such as images and multimedia, includes text descriptions to ensure that the content remains accessible to users who cannot see the visual elements of the interface. 

In addition, forms are designed to be accessible, with clear labels, and are easy to navigate and complete for users using screen readers such as [TalkBack](https://support.google.com/accessibility/android/topic/3529932?hl=en&ref_topic=9078845&sjid=8657868966461860489-NA) by Google, or other assistive technologies (AT).

### Color Vision Deficiency (CVD) Support
To ensure that text and other important components remain easy-to-use for users with CVD, we have made sure the application meets the WCAG 2.2 Level A minimum requirement of a 3:1 color contrast ratio between all elements. We have also taken the step to modify the color palette used in the application to enhance the visibility of chart elements for users with different types of CVD, as you can see in the visualizations below.

The following is Hot Stuff's chart color palette filtered to appear as how users with various types of CVD would see it:

'Normal' vision ![](https://github.com/PamCatten/hot-stuff/blob/main/assets/theme_normal.png)

Deuteranopic (red-green) vision ![](https://github.com/PamCatten/hot-stuff/blob/main/assets/theme_deuternanopia.png)

Protanopic (red-green) vision ![](https://github.com/PamCatten/hot-stuff/blob/main/assets/theme_protanopia.png)

Tritanopic (blue-yellow) vision ![](https://github.com/PamCatten/hot-stuff/blob/main/assets/theme_tritanopia.png)

As you can see, despite sharing the same hue, the colors in the palette closest in shade and staturation should still be distinctive enough for users with various types of CVD to differentiate between.

### Additional Language Support
While Hot Stuff currently only has English translations, we recognize the importance of language diversity and are committed to expanding our language support in the future for the benefit of a global audience. 

If you are fluent in a language that is not currently supported and want to contribute, we would love to have your help! The app strings are available for translation over in the Hot Stuff project on [Transifex](https://www.transifex.com/pamcatten/hot-stuff/). 

If the language you want to translate isn't listed within the Transifex project, feel free to [reach out](mailto:campatten.dev@outlook.com) and we'll add it when we see your email.

### Right-to-Left Text Support
While Hot Stuff currently only has English translations, we have included within the app several features to support right-to-left (RTL) languages, ensuring that users who read RTL scripts such as Arabic, Hebrew, and Farsi will be able to effectively use our project when translations are available.

Currently we support:
* Text within the application is aligned to the right by default for RTL languages, ensuring that content is displayed correctly and is easy to read
* Mirroring UI elements such as buttons, fields, and dialogs, to align with RTL reading, to hopefully provide a more natural RTL user experience

## Similar software
The format of Hot Stuff is entirely our own creation, which is unfortunate, because it means we put a lot time into re-inventing stuff that already exists! If, like us, you didn't know the name of this type of project, these apps are called home inventory or inventory management software. 

Due to its offline nature, lack of monetization, requiring no account or personal information, and a (relatively) lighter size, Hot Stuff still serves a niche unfilled by some of the other software listed below; with the caveat that the general structure will likely be similar to some, if not all of the other applications.  

Similar software that helps users record, sort, and list their items on Android (the list may be incomplete, and some appear to be unmaintained):
* [Sortly](https://play.google.com/store/apps/details?id=com.sortly.mythings&pcampaignid=web_share)
* [Encircle](https://play.google.com/store/apps/details?id=com.encircle&pcampaignid=web_share)
* [HouseBook](https://play.google.com/store/apps/details?id=chenige.chkchk.wairz&pcampaignid=web_share)
* [Smart Inventory](https://play.google.com/store/apps/details?id=com.nonzeroapps.android.smartinventory&pcampaignid=web_share)
* [ZenOwn](https://play.google.com/store/apps/details?id=com.zenown.app&hl=en&gl=US)
* [My Stuff Organizer](https://play.google.com/store/apps/details?id=com.ebizzapps.mystufforganizer&hl=en&gl=US)
* [Find My Stuff](https://play.google.com/store/apps/details?id=com.miquelcms.homeorganizer)

## Legal
#### Developers
This application is FOSS (Free and Open Source Software) licensed under the Apache License, Version 2.0. You can read the license in its entirety either [here](https://github.com/PamCatten/hot-stuff/blob/main/LICENSE) or on the [Apache website](https://www.apache.org/licenses/LICENSE-2.0).
#### Users
This app is subject to our [Terms of Use](https://github.com/PamCatten/hot-stuff/blob/main/TERMS.md) and [Privacy Policy](https://github.com/PamCatten/hot-stuff/blob/main/PRIVACY.md).

## Contact
For any inquiries or feedback, feel free to start a [discussion](https://github.com/PamCatten/hot-stuff/discussions), create an [issue](https://github.com/PamCatten/hot-stuff/issues), or reach out over [email](mailto:campatten.dev@outlook.com).
