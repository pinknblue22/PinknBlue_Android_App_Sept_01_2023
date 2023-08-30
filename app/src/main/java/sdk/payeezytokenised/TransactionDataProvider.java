package sdk.payeezytokenised;


import org.codehaus.jackson.annotate.JsonProperty;

public class TransactionDataProvider {

	//use these credentials after 14th oct
	public static String appIdCert ="VaYfAMZ1AcQMPGJwXOU4grMRn0P6sdfR";
	public static String tokenCert ="fdoa-34029d409bce588f7304d91c92f71c5b34029d409bce588f";


	public static String urlCert ="https://api-cert.payeezy.com/v1/transactions";
	public static String secureIdCert="7eaa8fd753793ec77357fb84d0c7e5bb00d2f653ed78b45ac633242882dec22d";
	public static String tokenUrl="https://api-cert.payeezy.com/v1/securitytokens?";
	public static String jsSecurityKey="js-a8c238ea79832567377c6749ef6d5101a8c238ea79832567";

	//German Direct Debit
	public static String appIdGD ="ehCz1VGlwNeeGcN5LjA5c2nvWKTnEZRn";
	public static String tokenGD ="fdoa-d790ce8951daa73262645cf102641994c1e55e7cdf4c03c8";
	public static String urlGD ="https://api-qa.payeezy.com/v1/transactions";
	public static String secIDGD="2b940ece234ee38131e70cc617aa2afa3d7ff8508856917958e7feb3gk289436";

	//Random number for merchantrefno
	@JsonProperty("merchant_ref")
	 static String merchantref;

	public static String getmerchantref()
	{
		return merchantref;
	}


}
