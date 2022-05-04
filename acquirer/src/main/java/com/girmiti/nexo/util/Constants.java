package com.girmiti.nexo.util;

public class Constants {
	
	private Constants() {
		//Do nothing
	}

	public static final String PATH = "src/main/resources/";
	public static final String CAAA_009_001_07 = "caaa.009.001.07.xsd";
	public static final String CAAA_011_001_08 = "caaa.011.001.08.xsd";
	public static final String CAAA_005_001_08 = "caaa.005.001.08.xsd";
	public static final String CAAA_007_001_08 = "caaa.007.001.08.xsd";
	public static final String CAAA_013_001_07 = "caaa.013.001.07.xsd";
	public static final String CAAA_016_001_06 = "caaa.016.001.06.xsd";
	public static final String CAAA_018_001_03 = "caaa.018.001.03.xsd";
	public static final String CAAA_001_001_08 = "caaa.001.001.08.xsd";
	public static final String CAAA_003_001_08 = "caaa.003.001.08.xsd";
	public static final String LOCALHOST = "http://localhost:";
	public static final String PORT = "9090";
	public static final String VERSION = "/1/0/";
	public static final String TRANSACTION = "/acquirer/acquirerService/transaction";
	public static final Integer SIZE = 10;
	public static final String ZERO = "0";
	public static final String SUCCESS = "SUCCESS";
	
	public static final String CONSUMES = "application/json";
	
	public static final String FIND_BY_CAPTURE_STATUS = "/findByCaptureStatus";
	
	public static final String EXPIRY_DATE_REGEX = "\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$"; 
	
	public static final String CURRENCY_ERROR = AcquirerProperties.getProperty("chatak.nexo.acquirer.curency");
	
	public static final String COUNTRY_ERROR = AcquirerProperties.getProperty("chatak.nexo.acquirer.country");
	
	public static final String POIDATA_ERROR = AcquirerProperties.getProperty("chatak.nexo.acquirer.poidata");
	
	public static final String DATASET_ERROR = AcquirerProperties.getProperty("chatak.nexo.acquirer.dataset");
	
	public static final String MERCHANTDATA_ERROR = AcquirerProperties.getProperty("chatak.nexo.acquirer.merchant");
	
	public static final String CONTEXT_ERROR = AcquirerProperties.getProperty("chatak.nexo.acquirer.context");
	
	public static final String POI_CAPABILITIES_ERROR = AcquirerProperties.getProperty("chatak.nexo.acquirer.capabilities");
	
	public static final String MESSAGE_FUNCTION_ERROR = AcquirerProperties.getProperty("chatak.nexo.acquirer.message.function");
	
	public static final String SALE_CONTEXT_ERROR = AcquirerProperties.getProperty("chatak.nexo.acquirer.sale.context");
	
	public static final String PLANE_CARD_DATA_ERROR = AcquirerProperties.getProperty("chatak.nexo.acquirer.plane.card.data");
	
	public static final String CARD_DATA_ERROR = AcquirerProperties.getProperty("chatak.nexo.acquirer.card.data");
	
	public static final String CONTEXT_DATA_ERROR = AcquirerProperties.getProperty("chatak.nexo.acquirer.context.data");
	
	public static final String MERCHANT_DATA_ERROR = AcquirerProperties.getProperty("chatak.nexo.acquirer.merchant.data");
	
	public static final String CAPTURE_ERROR = AcquirerProperties.getProperty("chatak.nexo.acquirer.transaction.capture");
	
	public static final String FAIL_REASON_ERROR = AcquirerProperties.getProperty("chatak.nexo.acquirer.fail.reason");
	
	public static final String ACQUIRER_DATA_ERROR = AcquirerProperties.getProperty("chatak.nexo.acquirer.data");
	
	public static final String PAN_ERROR = AcquirerProperties.getProperty("chatak.nexo.acquirer.pan.data");
	
	public static final String EXPIRY_DATE_ERROR = AcquirerProperties.getProperty("chatak.nexo.acquirer.expiry.date");
	
	public static final String DATE_TIME_FORMAT_ERROR = AcquirerProperties.getProperty("chatak.nexo.acquirer.date.time.format");
	
	public static final String PG_MOCK = AcquirerProperties.getProperty("ispgmock");
	
	public static final String ERROR_CODE = "01";
	
	public static final String SUCCESS_CODE = "00";
	
	public static final String ERROR_IN_TRANSACTION_DATA = AcquirerProperties.getProperty("chatak.nexo.acquirer.transaction.data");
	
	public static final String APPROVED = "APPROVED";
	
	public static final String PROCESS = "process";
	
	public static final String DUPLICATE_MESSAGE = AcquirerProperties.getProperty("chatak.nexo.acquirer.duplicate.message");
	
	public static final String REJECT_MESSAGE_FUNCTION = AcquirerProperties.getProperty("chatak.nexo.acquirer.notsupported.message");
	
	public static final String REJECT_INITIATING_PARTY = AcquirerProperties.getProperty("chatak.nexo.acquirer.notsupported.initiating.party");
	
	public static final String PROTOCOL_VERSION = AcquirerProperties.getProperty("chatak.nexo.acquirer.notsupported.protocal");

	public static final String CORRUPTED="corrupted";
	
	public static final String TXNAMOUNT=AcquirerProperties.getProperty("chatak.nexo.acquirer.notsupported.txnamount");

	public static final String TMSTRIGGER = AcquirerProperties.getProperty("chatak.nexo.acquirer.diagnostic.tmsTrigger");
	
	public static final String REJECTION_REMAINDER =  AcquirerProperties.getProperty("chatak.nexo.acquirer.diagnostic.rejection");
	
	public static final String OFFLINE_TRANSACTION_ERROR = AcquirerProperties.getProperty("chatak.nexo.acquirer.offline.transaction.error.message");
	
	public static final String TTLAMT_1= AcquirerProperties.getProperty("chatak.nexo.acquirer.diagnostic.totalamount"); 
	
	public static final String TTLAMT_2= AcquirerProperties.getProperty("chatak.nexo.acquirer.diagnostic.ttlamount");
	
	public static final String MAC_ERROR = AcquirerProperties.getProperty("chatak.nexo.acquirer.mac.error");
	
	public static final String SECURITY_KEY = AcquirerProperties.getProperty("chatak.nexo.acquirer.secrity.key");
	
	public static final String SECURITY_IV = AcquirerProperties.getProperty("chatak.nexo.acquirer.secrity.iv");
	
	public static final String SECURITY_MAC_KEY = AcquirerProperties.getProperty("chatak.nexo.acquirer.secrity.key.mac");
	
}