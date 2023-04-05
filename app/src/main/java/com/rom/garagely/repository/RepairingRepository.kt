package com.rom.garagely.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.rom.garagely.constant.Constant.SPACE_COLLECTION
import com.rom.garagely.helper.FirebaseDB
import com.rom.garagely.model.GarageSpace
import javax.inject.Inject

interface RepairingRepository {
    suspend fun getSpace(): List<GarageSpace>
}

class RepairRepositoryImp @Inject constructor(
    val db: FirebaseFirestore,
): RepairingRepository{


    override suspend fun getSpace(): List<GarageSpace> {
//        val spaces = db.collection(SPACE_COLLECTION)
////            .whereEqualTo()
//    }
        return emptyList()
    }
}