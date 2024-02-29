package com.hotstuff.ui.create

//class CreateItemFragmentTest {
//    private lateinit var scenario: FragmentScenario<CreateItemFragment>
//    @Before
//    fun init() {
//        scenario = launchFragmentInContainer(themeResId = R.style.AppTheme)
//        scenario.moveToState(Lifecycle.State.STARTED)
//    }
//    @Test
//    fun `submit item`() {
//        val testText = "Test"
//        val quantity = "1"
//        val value = "12.34"
//        onView(withId(R.id.create_name_text)).perform(typeText(testText))
//        onView(withId(R.id.create_quantity_text)).perform(typeText(quantity))
//        onView(withId(R.id.create_category_text)).perform(typeText(testText))
//        onView(withId(R.id.create_room_text)).perform(typeText(testText))
//        onView(withId(R.id.create_make_text)).perform(typeText(testText))
//        onView(withId(R.id.create_value_text)).perform(typeText(value))
//        onView(withId(R.id.create_description_text)).perform(typeText(testText))
//        Espresso.closeSoftKeyboard()
//        scrollTo()
//        onView(withId(R.id.button_create_item)).perform(scrollTo(), click())

//        onView(withText(R.string.toast_addItem_success))
//            .inRoot(withDecorView(not()))
//            .check(matches(isDisplayed()))
//        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
//        device.waitForIdle()
//        assertTrue(device.hasObject(By.text(R.string.toast_addItem_success.toString())))
//    }
//}