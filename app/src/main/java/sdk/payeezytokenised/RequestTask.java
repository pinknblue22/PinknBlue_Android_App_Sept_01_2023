package sdk.payeezytokenised;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;


import java.util.Random;
import java.util.UUID;

import firstdata.firstapi.client.FirstAPIClientV2Helper;
import firstdata.firstapi.client.domain.TransactionType;
import firstdata.firstapi.client.domain.v2.Token;
import firstdata.firstapi.client.domain.v2.TransactionRequest;
import firstdata.firstapi.client.domain.v2.TransactionResponse;
import firstdata.firstapi.client.domain.v2.Transarmor;
import firstdata.firstapi.client.domain.v2.UserTransactionResponse;


enum CardType {
	CARD_VISA ,
	CARD_MASTERCARD ,
	CARD_AMEX ,
	CARD_DISCOVER ,
	CARD_VALUELINK, 
	CARD_TELECHECK,
	CARD_NONE
};

enum TransactionCategory
{
	CATEGORY_NONE,
	CATEGORY_CVV,
	CATEGORY_CVV2,
	CATEGORY_AVS,
	CATEGORY_SPLITSHIPMENT,
	CATEGORY_LEVEL2,
	CATEGORY_LEVEL3,
	CATEGORY_SOFTDESCRIPTORS,
	CATEGORY_NAKEDREFUNDS,
	CATEGORY_NAKEDVOIDS,
	CATEGORY_3DS,
	CATEGORY_TRANSARMOR,
	CATEGORY_PAYPAL,
	CATEGORY_ZERODOLLAR,
	CATEGORY_RECURRING,
	CATEGORY_VALUELINK,
	CATEGORY_GENERATETOKEN,
	CATEGORY_FDTOKEN
};

enum TransactionTypePrimarySecondary
{
	AUTHORISE,
	PURCHASE,
	CREDIT,
	VOID,
	REFUND,
	NAKEDREFUND,
	CAPTURE

};


@SuppressLint("DefaultLocale")
public class RequestTask extends AsyncTask<String, String, String>{

	private Context context = null;
	sdk.payeezytokenised.TransactionDataProvider tdpbasic = new sdk.payeezytokenised.TransactionDataProvider();

	sdk.payeezytokenised.TransactionDataProviderL3 tdpl3  = new sdk.payeezytokenised.TransactionDataProviderL3();
	CardType cardtype = CardType.CARD_VISA;
	sdk.payeezytokenised.CardType cardtypeSecondary = sdk.payeezytokenised.CardType.CARD_VISA;
	sdk.payeezytokenised.TransactionCategory category = sdk.payeezytokenised.TransactionCategory.CATEGORY_AVS;


	FirstAPIClientV2Helper clientHelper = new FirstAPIClientV2Helper();

	Random rand=new Random();
	public RequestTask(Context pcontext)
	{
		context = pcontext;

	}
	private String statusString = "";
	private String splitter = "~~~~~~~~";

	
    @Override
    protected String doInBackground(String... uri) {
    	//Added for GETgettoken aug 3rd
		if(uri[0].toLowerCase().equalsIgnoreCase("gettokenvisa"))
		{
			CallGenerateGETTokenVisaGetToken(uri);

			return "TokenGeneration GET";
		}
		//Added for GETauthorisetoken aug 6th
		if(uri[0].toLowerCase().equalsIgnoreCase("getauthorisetoken"))
		{
			CallAuthorizePurchaseVisaGetGetToken(uri, TransactionTypePrimarySecondary.AUTHORISE);
			return "Authorise Response";
		}

		//Added for GETpurchasetoken aug 6th
		if(uri[0].toLowerCase().equalsIgnoreCase("getpurchasetoken"))
		{
			CallAuthorizePurchaseVisaGetGetToken(uri, TransactionTypePrimarySecondary.PURCHASE);
			return "Purchase Response";
		}

		//Added for GETauthcapturetoken aug 6th
		if(uri[0].toLowerCase().equalsIgnoreCase("getauthcapturetoken"))
		{
			CallAuthorizePurchaseVisaGetGetToken(uri, TransactionTypePrimarySecondary.AUTHORISE);
			CallCaptureRefundVoidVisaGetGetToken(TransactionTypePrimarySecondary.CAPTURE);
			return "Auth + Capture Response";
		}

//Added for GETauthvoidtoken aug 6th
		if(uri[0].toLowerCase().equalsIgnoreCase("getauthvoidtoken"))
		{
			CallAuthorizePurchaseVisaGetGetToken(uri, TransactionTypePrimarySecondary.AUTHORISE);
			CallCaptureRefundVoidVisaGetGetToken(TransactionTypePrimarySecondary.VOID);
			return "Auth + Void Response";
		}
//Added for GETpurchaserefundtoken aug 6th
		if(uri[0].toLowerCase().equalsIgnoreCase("getpurchaserefundtoken"))
		{
			CallAuthorizePurchaseVisaGetGetToken(uri, TransactionTypePrimarySecondary.PURCHASE);
			CallCaptureRefundVoidVisaGetGetToken(TransactionTypePrimarySecondary.REFUND);
			return "Purchase + Refund Response";
		}

    	return "authorise";
    }

    @Override
    protected void onPostExecute(String result) {
		super.onPostExecute(result);

		//MainActivity.displayResult.setText(result);

		Log.e("bbb","Button Authorize Clicked" + result);
		String abc=result + statusString;

		Log.e("bbb","resul" +" " +abc);
	  //  System.out.println("Button Authorize Clicked" + result);

    }
    

public void Initialise()
{
	clientHelper.setAppId(TransactionDataProvider.appIdCert);
	clientHelper.setSecuredSecret(TransactionDataProvider.secureIdCert);
	clientHelper.setToken(TransactionDataProvider.tokenCert);
	clientHelper.setUrl(TransactionDataProvider.urlCert);

}
	//Method added for GET token August 3rd
	private void CallGenerateGETTokenVisaGetToken(String[] uri)
	{
		try
		{
			Initialise();

			clientHelper.setTokenurl(TransactionDataProvider.tokenUrl);
			clientHelper.setJsSecurityKey(TransactionDataProvider.jsSecurityKey);

			String url=clientHelper.getTokenurl()+"auth="+uri[1]+"&apikey="+clientHelper.getAppId()+"&js_security_key="+
					clientHelper.getJsSecurityKey()+"&callback="+uri[2]+"&currency="+uri[3]+"&type="+uri[4]+"&credit_card.type="+uri[5]
					+"&credit_card.cardholder_name="+uri[6]+"&credit_card.card_number="+uri[7]+"&credit_card.exp_date="+uri[8]
					+"&credit_card.cvv="+uri[9];


			TransactionResponse response = clientHelper.doPrimaryTransactionGET(url);

			System.out.println("Token gen="+TransactionResponse.getToken().getTokenData().getValue());

			statusString = "gettoken called    token generated ==";
			statusString = statusString  +TransactionResponse.getToken().getTokenData().getValue();
			statusString = statusString  + ((UserTransactionResponse)response).getResponseString() + splitter;

			System.out.println("Response : " + response.toString());
		}catch ( Exception e)
		{
			System.out.println(e.getMessage());
			statusString = statusString + "Exception :"+ e.getMessage() + splitter;
		}
	}

//for GET method
	private void CallAuthorizePurchaseVisaGetGetToken(String[] uri,TransactionTypePrimarySecondary transactionType)
	{
		try
		{
		Initialise();

			category = sdk.payeezytokenised.TransactionCategory.CATEGORY_FDTOKEN;
			TransactionRequest trans = getPrimaryTransactionForTransType(uri);

			if ((transactionType) .equals (TransactionTypePrimarySecondary.AUTHORISE))
				trans.setTransactionType(TransactionType.AUTHORIZE.name().toLowerCase());
			else
				trans.setTransactionType(TransactionType.PURCHASE.name().toLowerCase());

				Object responseObject2 =clientHelper.doPrimaryTransactionObject(trans);

			statusString = statusString + ((UserTransactionResponse)responseObject2).getResponseString() + splitter;
			/*UserTransactionResponse uresp2 = (UserTransactionResponse)(responseObject2);
			TransactionResponse resp2 = uresp2; //uresp.getBody();
			System.out.println(uresp2.getResponseMessage() );
			statusString = statusString + uresp2.getResponseString() + splitter;*/
			/*if(resp2.getTransactionStatus() == "approved")
			{
				System.out.println(uresp2.getTransactionStatus() );
			}*/
		}catch(Exception e)
		{
			statusString = statusString + "Exception :"+ e.getMessage() + splitter;
		}
	}

	public void randomnumbergeneration()
	{
		UUID uuid=UUID.randomUUID();
		int newrandom=rand.nextInt( Integer.MAX_VALUE ) + 1;

		TransactionDataProvider.merchantref=null;
		TransactionDataProvider.merchantref=uuid.toString();

	}


	//For Capture using TokenGeneration-GET method
	private void CallCaptureRefundVoidVisaGetGetToken(TransactionTypePrimarySecondary transactionType)
	{
		try
		{
			cardtypeSecondary = sdk.payeezytokenised.CardType.CARD_VISA;

		Initialise();

			TransactionRequest trans =new TransactionRequest();

	if ((transactionType) .equals (TransactionTypePrimarySecondary.CAPTURE))
				trans.setTransactionType(TransactionType.CAPTURE.name().toLowerCase());
	else if ((transactionType) .equals (TransactionTypePrimarySecondary.VOID))
				trans.setTransactionType(TransactionType.VOID.name().toLowerCase());
	else if ((transactionType) .equals (TransactionTypePrimarySecondary.REFUND))
				trans.setTransactionType(TransactionType.REFUND.name().toLowerCase());

			trans.setReferenceNo(TransactionResponse.getTransactionId());

			trans=getpayloadforsecondary(trans);

			TransactionResponse responseObject4 = clientHelper.doSecondaryTransactionObject(trans);
			TransactionResponse resp4 = (TransactionResponse)(responseObject4);
			statusString = statusString + ((UserTransactionResponse)responseObject4).getResponseString() + splitter;

		}catch(Exception e)
		{
		}
	}


    public TransactionRequest getPrimaryTransactionForTransType(String[] uri) {
    	TransactionRequest request=new TransactionRequest();

			randomnumbergeneration();

			request.setReferenceNo("AstonishingSale" + TransactionDataProvider.merchantref);
			request.setPaymentMethod(uri[1]);
			request.setAmount(uri[2]);
			request.setCurrency(uri[3]);

			Token token = new Token();
			Transarmor ta = new Transarmor();
			ta.setValue(TransactionResponse.getToken().getTokenData().getValue());
	//	ta.setValue("12345678");
			System.out.println("token value from authorise"+TransactionResponse.getToken().getTokenData().getValue());
			ta.setName(TransactionResponse.getToken().getTokenData().getName());
			ta.setExpiryDt(TransactionResponse.getToken().getTokenData().getExpiryDt());
			ta.setType(TransactionResponse.getToken().getTokenData().getType());
			token.setTokenData(ta);

			token.setToken_type("FDToken");
			request.setToken(token);
			System.out.println("Token data in authorise"+TransactionResponse.getToken().getTokenData().getValue());
			request.setCard(null);
			request.setBilling(null);
			request.setAuth(null);
			request.setTa_token(null);
			request.setType(null);

        return request;
    }

    private TransactionRequest getpayloadforsecondary(TransactionRequest trans) {
		trans.setPaymentMethod(TransactionResponse.getMethod());
		trans.setAmount(TransactionResponse.getAmount());
		trans.setCurrency(TransactionResponse.getCurrency());

		trans.setTransactionTag(TransactionResponse.getTransactionTag());
		trans.setId(TransactionResponse.getTransactionId());
		Token token = new Token();
		Transarmor ta = new Transarmor();


		ta.setValue(TransactionResponse.getToken().getTokenData().getValue());
		ta.setName(TransactionResponse.getToken().getTokenData().getName());
		ta.setExpiryDt(TransactionResponse.getToken().getTokenData().getExpiryDt());
		ta.setType(TransactionResponse.getToken().getTokenData().getType());

		token.setTokenData(ta);
		token.setToken_type("FDToken");
		trans.setToken(token);


		return trans;
    }

}