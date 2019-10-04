package com.transport.xianxian.net;


/**
 * URL路径处理类
 */
public class URLs {
    //   public static String HOST = "http://app.zcashplan.com";
//    public static String HOST = "http://192.168.0.200";
    public static final String PROJECT_NAME = "";
    public static final String API = "";

    /**
     * 更新
     */
    public static final String Upgrade = "/api/article/sys-upgrade";
    /**
     * 发送验证码
     */
    public static final String Code = "/api/sms-code/send-code";
    /**
     * 登录
     */
    public static final String Login = "/api/member/login";
    /**
     * 一键登录
     */
    public static final String Login1 = "/api/member/quick-login";
    /**
     * 登录 - 不是同一手机号 -授权
     */
    public static final String Login_Authorization = "/api/member/login-verify";
    /**
     * 重置密码
     */
    public static final String ForgetPassword = "/api/member/reset-password";
    /**
     * 注册
     */
    public static final String Registered = "/api/member/register";

    /**
     * 注册-极验
     */
    public static final String Registered_API1 = "/api/sms-code/send-code";
    /**
     * 新人领取
     */
    public static final String NewcomerReward= "/api/member/sign";
    /**
     * 实名认证
     */
    public static final String Verified = "/api/member/certification";

    //算力
    /**
     * 算力
     */
    public static final String Fragment1= "/api/withdrawal/create";
    /**
     * 算力
     */
    public static final String SuanLiList= "/api/invest/record";

    //区块
    /**
     * 区块
     */
    public static final String Fragment2 = "/api/top-up/create";
    /**
     * 购买算力
     */
    public static final String BuyComputingPower = "/api/invest/create";
    /**
     * 购买区块
     */
    public static final String BuyQuKuai = "/api/block/create";
    //首页
    /**
     * 首页
     */
    public static final String Fragment3 = "/api/like-game/index";
    /**
     * 游戏参与（加密）
     */
    public static final String Game_Add = "/api/like-game-participation/create";
    /**
     * 往期列表
     */
    public static final String PastList = "/api/like-game/history";
    /**
     * 排行榜
     */
    public static final String Leaderboard = "/api/like-game/rank";
    /**
     * 详情
     */
    public static final String GameDetail = "/api/like-game/detail";
    //充值
    /**
     * 充值
     */
    public static final String Fragment4 = "/api/top-up/create";
    /**
     * 银行卡设置
     */
    public static final String Collection = "/api/member/set-proceeds";
    /**
     * 银行卡设置_验证码
     */
    public static final String Collection_code = "/api/sms-code/send-code";

    // 用户模块
    /**
     * 我的主页
     */
    public static final String Center = "/api/member/center";
    /**
     * 个人信息
     */
    public static final String Info = "/api/member/get-info";
    /**
     * 修改信息
     */
    public static final String ChangeProfile = "/api/member/update";
    /**
     * 设置交易密码
     */
    public static final String TransactionPassword = "/api/member/set-trade-password";
    /**
     * 修改密码
     */
    public static final String ChangePassword = "/api/member/update-password";
    /**
     * 钱包地址（get）
     */
    public static final String WalletAddress = "/api/member/set-wallet-addr";
    /**
     * 账户详情1(我的余额进入)
     */
    public static final String AccountDetail2 = "/api/member/account";
    /**
     * 账户详情2(我的区块进入)
     */
    public static final String AccountDetail1 = "/api/member/account2";
    /**
     * 我的充值
     */
    public static final String MyRecharge = "/api/top-up/record";
    /**
     * 充值详情
     */
    public static final String RechargeDetail = "/api/top-up/detail";
    /**
     * 取消充值
     */
    public static final String RechargeDetail_Cancel = "/api/top-up/cancel";
    /**
     * 提币（get）
     */
    public static final String TakeCash = "/api/withdrawal/create";
    /**
     * 提现列表
     */
    public static final String MyTakeCash = "/api/withdrawal/record";
    /**
     * 我的区块列表
     */
    public static final String MyQuKuai = "/api/block/record";
    /**
     * 提现详情
     */
    public static final String TakeCashDetail = "/api/withdrawal/detail";
    /**
     * 我的游戏
     */
    public static final String MyGame = "/api/like-game-participation/record";
    /**
     * 我的推广
     */
    public static final String Share = "/api/member/team";
    /**
     * 推广用户
     */
    public static final String DirectMember = "/api/member/direct-recommend";
    /**
     * 公告详情
     */
    public static final String NoticeDetail = "/api/article/notice";
    /**
     * 评分详情
     */
    public static final String ScoreDetail = "/api/article/notice";
    /**
     * 钱包
     */
    public static final String Wallet = "/api/article/notice";
    /**
     * 资金统计
     */
    public static final String CapitalStatistics = "/api/article/notice";
    /**
     * 帮助列表
     */
    public static final String Help = "/api/article/help";
    /**
     * 在线客服
     */
    public static final String OnlineService = "/api/leave-message/record";
    /**
     * 添加留言
     */
    public static final String AddMessage = "/api/leave-message/create";
    /**
     * 我的海报
     */
    public static final String MyPoster = "/api/index/share";
    /**
     * 创建转币（获取可用金额）
     */
    public static final String Transfer= "/api/transfer/create";
    /**
     * 转币记录
     */
    public static final String TransferRecord= "/api/transfer/record";
    /**
     * 拼接请求路径
     *
     * @PARAM URI
     * @RETURN
     */
  /*  public static String getURL(String uri) {
        return HOST + PROJECT_NAME + API + uri;
    }*/

}
