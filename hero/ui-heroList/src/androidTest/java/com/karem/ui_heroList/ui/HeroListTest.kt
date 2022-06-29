package com.karem.ui_heroList.ui

import androidx.compose.runtime.remember
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import com.karem.hero_datasource_test.network.data.HeroDataValid
import com.karem.hero_datasource_test.network.serializeHeroData
import com.karem.ui_heroList.coil.FakeImageLoader
import com.karem.ui_herolist.ui.HeroList
import com.karem.ui_herolist.ui.HeroListState
import org.junit.Rule
import org.junit.Test

class HeroListTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val imageLoader = FakeImageLoader.build(context)
    private val heroData = serializeHeroData(HeroDataValid.data)

    @Test
    fun areHerosShown(){
        composeTestRule.setContent {
            val state = remember{
                HeroListState(
                    heros = heroData,
                    filteredHeros = heroData
                )
            }
            HeroList(
                state = state,
                events = {},
                imageLoader = imageLoader,
                navigateToDetailsScreen = {}
            )
        }
        composeTestRule.onNodeWithText("Anti-Mage").assertIsDisplayed()
        composeTestRule.onNodeWithText("Max").assertIsDisplayed()
        composeTestRule.onNodeWithText("Bane").assertIsDisplayed()


    }
}