package com.mitteloupe.solid.solidactivity.handler

import android.app.Activity

interface LocalVoiceInteractionHandler {
    /**
     * @see Activity.onLocalVoiceInteractionStarted
     */
    fun onLocalVoiceInteractionStarted() = Unit

    /**
     * @see Activity.onLocalVoiceInteractionStopped
     */
    fun onLocalVoiceInteractionStopped() = Unit
}