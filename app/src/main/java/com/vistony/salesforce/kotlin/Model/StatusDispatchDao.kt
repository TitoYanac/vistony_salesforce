package com.vistony.salesforce.kotlin.Model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StatusDispatchDao {
    /*@Query("SELECT T0.DocEntry,(SELECT * FROM statusdispatch where IFNULL(chk_statusServerDispatch,'N')='N' AND T0.DocEntry=DocEntry ) FROM statusdispatch T0 where IFNULL(chk_statusServerDispatch,'N')='N' ")
    fun getAPIStatusDispatch(): LiveData<List<SendAPIStatusDispatch>>*/

    @Query("SELECT * FROM statusdispatch WHERE IFNULL(chk_statusServerDispatch,'N')='N' GROUP BY DocEntry LIMIT 1")
    fun getControlIdWithNullStatusServer(): StatusDispatch?

    /*@Query("SELECT * FROM statusdispatch WHERE IFNULL(chk_statusServerDispatch,'N')='N' AND DocEntry=:controlId LIMIT 1")
    fun getHeaderStatusDispatchByControlId(controlId: String): List<StatusDispatch>*/

    @Query("SELECT fuerzatrabajo_id,fuerzatrabajo,Delivered,ReturnReason,entrega_id,Comments,LineId,DeliveryNotes,ReturnReasonText,id,domembarque_id FROM statusdispatch WHERE IFNULL(chk_statusServerDispatch,'N')='N' AND DocEntry=:controlId LIMIT 10")
    fun getDetailListStatusDispatchByControlId(controlId: String): List<StatusDispatch>

    @Query("SELECT * FROM statusdispatch WHERE IFNULL(chk_statusPhotoServerDispatch,'N')='N' GROUP BY DocEntry LIMIT 1")
    fun getControlIdWithNullPhotoStatusServer(): StatusDispatch?

    @Query("SELECT fuerzatrabajo_id,fuerzatrabajo,Delivered,ReturnReason,entrega_id,Comments,PhotoStore,PhotoDocument,LineId,DeliveryNotes,ReturnReasonText,id,domembarque_id  FROM statusdispatch WHERE IFNULL(chk_statusPhotoServerDispatch,'N')='N' AND DocEntry=:controlId LIMIT 3")
    fun getDetailListPhotoStatusDispatchByControlId(controlId: String): List<StatusDispatch>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addStatusDispatch(statusDispatch: List<StatusDispatch>?)

    @Query("DELETE FROM statusdispatch")
    fun deleteStatusDispatch()

    @Query("UPDATE statusdispatch SET checkintime = :checkintime, checkouttime = :checkouttime, latitud = :latitud, longitud = :longitud  WHERE domembarque_id = :domembarque_id and cliente_id=:cliente_id ")
    fun updateStatusDispatch(checkintime: String?, checkouttime: String?, latitud: String?, longitud: String?, domembarque_id: String?, cliente_id: String?)

    @Query("UPDATE statusdispatch SET chk_statusServerDispatch = 'Y', messageServerDispatch = :Message WHERE DocEntry = :DocEntry and LineId=:LineId ")
    fun updateStatusAPIDispatch(DocEntry: String?,LineId: String?, Message: String?)

    @Query("UPDATE statusdispatch SET chk_statusPhotoServerDispatch = 'Y', messageServerDispatch = :Message WHERE DocEntry = :DocEntry and LineId=:LineId ")
    fun updatePhotoStatusAPIDispatch(DocEntry: String?,LineId: String?, Message: String?)
}