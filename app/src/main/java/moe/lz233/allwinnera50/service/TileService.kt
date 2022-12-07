package moe.lz233.allwinnera50.service

import android.content.Intent
import android.service.quicksettings.Tile
import moe.lz233.allwinnera50.App
import moe.lz233.allwinnera50.logic.dao.ConfigDao
import moe.lz233.allwinnera50.ui.process.ProcessServiceActivity
import moe.lz233.allwinnera50.util.LogUtil

class TileService : android.service.quicksettings.TileService() {
    override fun onTileAdded() {
        super.onTileAdded()
        qsTile.state = Tile.STATE_INACTIVE
        qsTile.updateTile()
    }

    override fun onClick() {
        super.onClick()
        when (qsTile.state) {
            Tile.STATE_UNAVAILABLE -> {
                startForegroundService(Intent(this, KeyBoardService::class.java))
                qsTile.state = Tile.STATE_ACTIVE
            }
            Tile.STATE_INACTIVE -> {
                startForegroundService(Intent(this, KeyBoardService::class.java))
                qsTile.state = Tile.STATE_ACTIVE
            }
            Tile.STATE_ACTIVE -> {
                startForegroundService(Intent(this, KeyBoardService::class.java).apply {
                    putExtra("stopService", true)
                })
                qsTile.state = Tile.STATE_INACTIVE
            }
        }
        qsTile.updateTile()
    }

    override fun onStartListening() {
        super.onStartListening()
        qsTile.state = if (App.isServiceExisted(KeyBoardService::class.java)) {
            Tile.STATE_ACTIVE
        } else {
            Tile.STATE_INACTIVE
        }
        qsTile.updateTile()
    }
}