package app.project.ui.navigation

interface ScreenNavigator {
    fun goBackScreen()
    fun resetMainScreen()
    fun displayNewsFeedAsNavRoot()
    fun navigateToSignIn()
    fun navigateToSignUp()
    fun displayGettingStartedAsNavRoot()
    fun displayIntroductionAsNavRoot()
}