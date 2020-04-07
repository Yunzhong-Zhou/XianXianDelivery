package com.delivery.xianxian.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.delivery.xianxian.activity.LoginActivity;
import com.delivery.xianxian.utils.CommonUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler{
	private static String TAG = "MicroMsg.WXEntryActivity";
    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    	api = WXAPIFactory.createWXAPI(this, "wx79d0350178a9ff3a", false);
        try {
            Intent intent = getIntent();
        	api.handleIntent(intent, this);
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
        finish();
	}

	@Override
	public void onResp(BaseResp resp) {
		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
			//成功
			if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
				SendAuth.Resp authResp = (SendAuth.Resp)resp;
				final String code = authResp.code;

				Bundle bundle = new Bundle();
				bundle.putString("code", code);
				CommonUtil.gotoActivityWithData(WXEntryActivity.this, LoginActivity.class,bundle,true);
			}

			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			//用户取消
			Toast.makeText(this, "用户取消授权", Toast.LENGTH_SHORT).show();
			CommonUtil.gotoActivity(WXEntryActivity.this, LoginActivity.class,true);
			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			//用户拒绝
			Toast.makeText(this, "用户拒绝授权", Toast.LENGTH_SHORT).show();
			CommonUtil.gotoActivity(WXEntryActivity.this, LoginActivity.class,true);
			break;
		default:
			Toast.makeText(this, "微信登录失败", Toast.LENGTH_SHORT).show();
			CommonUtil.gotoActivity(WXEntryActivity.this, LoginActivity.class,true);
			break;
		}


	}
}