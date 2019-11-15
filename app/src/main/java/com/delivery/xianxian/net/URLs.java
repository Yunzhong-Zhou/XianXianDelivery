package com.delivery.xianxian.net;


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
     * 引导页
     */
    public static final String Guide = "/api/app-banner/index";
    /**
     * 发送验证码
     */
    public static final String Code = "/api/sms-code/send-code";
    /**
     * 登录
     */
    public static final String Login = "/api/owner/login";
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
    public static final String ForgetPassword = "/api/owner/forget-password";
    /**
     * 注册
     */
    public static final String Registered = "/api/owner/register";
    /**
     * 注册2
     */
    public static final String Registered2= "/api/owner/identity";
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

    /**
     * 首页
     */
    public static final String Fragment1= "/api/owner/index";
    /**
     * 接单列表
     */
    public static final String Fragment1List= "/api/owner/tindent/set";
    /**
     * 订单详情
     */
    public static final String OrderDetails= "/api/owner/tindent/detail";
    /**
     * 拒绝/接单
     */
    public static final String OrderDetails_JieDan= "/api/owner/tindent/create";
    /**
     * 确认的类型1确认装货2提醒司机装货3确认卸货4附加费5转单确认6取消订单7接收转单8获取附加费
     */
    public static final String OrderDetails_ZhuangHuo= "/api/owner/tindent/confirm";
    /**
     * 订单
     */
    public static final String Fragment2 = "/api/owner/tindent/search";
    /**
     * 获取附加费
     */
    public static final String AddSurcharge = "/api/owner/tindent/search";
    /**
     * 添加地址
     */
    public static final String AddAddress = "/api/owner/addr-create";

    // 用户模块
    /**
     * 我的主页
     */
    public static final String Center = "/api/owner/center/index";
    /**
     * 退出
     */
    public static final String Out = "/api/owner/logout";
    /**
     * 修改手机号
     */
    public static final String ChangePhone = "/api/owner/center/bind-mobile";
    /**
     * 修改信息
     */
    public static final String ChangeProfile = "/api/owner/center/update";
    /**
     * 设置交易密码
     */
    public static final String TransactionPassword = "/api/member/set-trade-password";
    /**
     * 修改密码
     */
    public static final String ChangePassword = "/api/owner/center/reset-password";
    /**
     * 钱包地址（get）
     */
    public static final String WalletAddress = "/api/member/set-wallet-addr";
    /**
     * 积分商城
     */
    public static final String JiFenShangCheng = "/api/goods/index";
    /**
     * 积分列表
     */
    public static final String JiFenLieBiao = "/api/goods/search";
    /**
     * 积分列表-兑换
     */
    public static final String JiFenLieBiao_add = "/api/goods/exchange-add";
    /**
     * 兑换记录
     */
    public static final String DuiHuanJiLu = "/api/goods/exchange-search";
    /**
     * 积分明细
     */
    public static final String JiFenMingXi = "/api/goods/tscore-search";
    /**
     * 奖励记录
     */
    public static final String JiangLiList = "/api/owner/center/award";

    /**
     * 车主认证
     */
    public static final String Auth_CheZhu = "/api/owner/center/identification";
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
    public static final String NoticeDetail = "/api/owner/message";
    /**
     * 评分详情
     */
    public static final String ScoreDetail = "/api/owner/tcomment";
    /**
     * 钱包
     */
    public static final String Wallet = "/api/owner/center/tmoney";
    /**
     * 资金统计
     */
    public static final String CapitalStatistics = "/api/owner/tmoney-today";
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
