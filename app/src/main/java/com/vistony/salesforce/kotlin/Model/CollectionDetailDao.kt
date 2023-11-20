package com.vistony.salesforce.kotlin.Model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CollectionDetailDao {
    @Query("SELECT * FROM collectiondetail WHERE StatusSendAPI = 'N' AND CompanyCode=:CompanyCode AND UserID=:UserID")
    fun getCollectionDetailSendAPIList(CompanyCode: String,UserID:String): List<CollectionDetail>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addListCollectionDetail(collectionDetailList: List<CollectionDetail>?)

    @Query("Select  max(CAST(Receip AS INTEGER)) ultimorecibo from collectiondetail  where CompanyCode=:CompanyCode and UserID=:UserID ")
    fun getLastReceip(CompanyCode: String,UserID:String): String

    @Query("SELECT * FROM collectiondetail where Receip=:Receip and UserID=:UserID ")
    fun getCollectionDetailUnit(Receip: String,UserID: String): List<CollectionDetail>

    @Query("SELECT * FROM collectiondetail where Receip=:Receip and UserID=:UserID ")
    fun getCollectionDetailObj(Receip: String,UserID: String): List<CollectionDetail>

    @Query("UPDATE collectiondetail SET APICode = :APICode, APIMessage = :APIMessage, StatusSendAPI = :StatusSendAPI WHERE Receip = :Receip and UserID=:UserID ")
    fun updateCollectionDetailAPI(Receip: String, UserID: String, APICode: String, APIMessage: String, StatusSendAPI: String)

    @Query("SELECT * FROM collectiondetail where StatusDeposit='N' and CANCELED='N' and QRStatus='Y' and IncomeDate=:IncomeDate ")
    fun getCollectionDetailPendingDeposit(IncomeDate: String): List<CollectionDetail>

    @Query("SELECT IFNULL(COUNT(Receip),0) as Receip FROM collectiondetail")
    fun getCountCollectionDetail(): Int

    @Query("UPDATE collectiondetail SET Deposit = :Deposit, BankID = :BankID,StatusDeposit=:StatusDeposit,StatusCancelDeposit=:StatusCancelDeposit WHERE Receip = :Receip ")
    fun updateDepositCollectionDetail(Receip: String, Deposit: String, BankID: String, StatusDeposit:String,StatusCancelDeposit:String)

    @Query("SELECT APICode,Deposit,BankID,Receip,QRStatus  FROM collectiondetail where StatusDeposit='Y' and CANCELED='N' and StatusSendAPIDeposit='N' ")
    fun getCollectionDetailDeposited(): List<CollectionDetailPendingDeposit>

    @Query("UPDATE collectiondetail SET StatusSendAPIDeposit = 'Y', StatusSendAPIQR = 'Y' WHERE Receip = :Receip ")
    fun updateDepositReceiveCollectionDetail(Receip: String)

    @Query("SELECT   AmountCharged,AppVersion,Balance,BankID,Banking,Brand,CancelReason,CardCode,CollectionCheck,Commentary," +
            "Deposit,DirectDeposit,DocEntryFT,DocNum,DocTotal,IncomeDate,IncomeTime,Intent,ItemDetail,Model,NewBalance," +
            "OSVersion,POSPay,QRStatus,Receip,SlpCode,Status,U_VIS_CollectionSalesperson,U_VIS_Type,UserID FROM collectiondetail WHERE StatusSendAPI = 'N' AND CompanyCode=:CompanyCode AND UserID=:UserID")
    fun sendAPICollectionDetail(CompanyCode: String,UserID:String): List<CollectionDetailAPI>

    @Query("SELECT * FROM collectiondetail where IncomeDate=:IncomeDate ")
    fun getCollectionDetailForDate(IncomeDate: String): List<CollectionDetail>

    @Query("SELECT * FROM collectiondetail where Deposit=:deposit and  Status='P' ")
    fun getCollectionDetailForDeposit(deposit: String): List<CollectionDetail>

    @Query("SELECT APICode,Deposit,BankID,Receip,QRStatus  FROM collectiondetail where StatusCancelDeposit='Y' and StatusDeposit='N' ")
    fun getCollectionDetailDepositCancel(): List<CollectionDetailPendingDeposit>

}