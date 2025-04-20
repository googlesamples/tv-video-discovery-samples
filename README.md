# Tv Video Discovery Samples

This repository contains samples demonstrating how a 3p app can integrate with
the video discovery APIs to send personalized user data to Google.

## When should I invoke the APIs?

> Important: The publish APIs are overwrite APIs. Every new publish API call
> will overwrite the previously published requests for that specific
> `AccountProfile`. So, please don't invoke the delete API to clear Continue
> Watching row because delete APIs are reserved for special instances. Instead,
> just invoke the publish APIs with empty list in the request body.

> Important: Remember to set the `syncAcrossDevices` flag to true (or false if
> user hasn't consented to share the data with Google) in both the publish as
> well as delete requests regardless of the PublishReason or DeleteReason.

1. **Publish Continuation Clusters API**: This api should be invoked on the
   following user events:
   - User logs in to the account. If your app supports multiple profiles within
     the same account, then invoke the publish API after the user selects the
     profile
   - User switches between profiles (only applicable if your app supports
     multi-profile accounts)
   - User pauses/stops playing a video. This includes publishing when user hits
     the back button to go back to previous page while the video playback was in
     progress. This also includes app exit when the playback is in progress.
   - User scrubs the video forwards or backwards
   - User completes watching a video. The Continue Watching request should now
     not contain the completed video. If the video is part of a series (Movie or
     Tv Show), then add the next video to the Continue Watching request with
     correct `WatchNextType`.
   - User removes an item from your app's Continue Watching row. The Continue
     Watching request should now not contain the removed entity.
1. **Delete Clusters API**: Use the correct DeleteReason when sending the delete
   request. Invoke the Delete Clusters API on the following events:
   - User logs out of the account
   - User revokes consent to share data with Google
   - User deletes a profile within an account
   - User deletes the entire account

## Samples

- **android-kotlin**: Demonstrates how an Android Tv and Android Mobile app
  written in Kotlin can integrate with Engage SDK to publish user personalized
  data.

## Contributing

To report a bug or raise a feature request, please use GitHub issues. Please
check with the contributors if it would be ok for you to work on it. An approved
GitHub issue is required along with your Pull request.

You will also need to sign the CLA. When you raise a PR, the bot will ask you to
do that.

> Note: A pull request without an approved issue will be closed without giving
> any reason.

## Disclaimer

This project is intended for demonstration purposes only and is not eligible for
the
[Google Open Source Software Vulnerability Rewards Program](https://bughunters.google.com/open-source-security).
