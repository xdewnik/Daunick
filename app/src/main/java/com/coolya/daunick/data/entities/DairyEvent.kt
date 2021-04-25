package com.coolya.daunick.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class DiaryEvent(
    var thoughts: String,
    var emo: String,
    var event: String,
    var scale: String,
    var time: Long,
    @PrimaryKey(autoGenerate = true)
    val dairyId: Long = 0
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DiaryEvent

        if (thoughts != other.thoughts) return false
        if (emo != other.emo) return false
        if (event != other.event) return false
        if (time != other.time) return false
        if (dairyId != other.dairyId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = thoughts.hashCode()
        result = 31 * result + emo.hashCode()
        result = 31 * result + event.hashCode()
        result = 31 * result + time.hashCode()
        result = 31 * result + dairyId.hashCode()
        return result
    }
}