package com.smona.app.evaluationcar.framework.upload;

public final class ErrorCode {

    public static final int NONE = -1;

    public static final int OK = 0;

    public static final int NETWORK_DISCONNECTION = 1;
    public static final int NETWORK_BUSY = 2;
    public static final int NETWORK_ERROR = 3;
    public static final int NETWORK_CONNETED_TIMEOUT = 4;
    public static final int NETWORK_INVALID_RESPONSE = 5;

    public static final int UNLOGIN = 10001;
    public static final int VERSION_UNSUPPORTED = 10002;

    public static final int ACCOUNT_REGISTER_LIMIT = 20000; // 限制账号注册
    public static final int USERNAME_REPEAT = 20008; // 用户名已使用
    public static final int ACCOUNT_TOKEN_FAIL = 20004;     //token过期了
    public static final int ACCOUNT_IS_LOCK = 20007;             //帐号被冻结
    public static final int ACCOUNT_POST_NEED_IMG = 20202;      //必须有图片
    public static final int POST_NOT_EXIST = 20203;       //帖子不存在
    public static final int POST_ILLEGAL = 20204;               //帖子有非法词
    public static final int VOTE_INVALID = 20208;            //已经投过票了
    public static final int COMMENT_ILLEGAL = 20401;            //评论有非法词
    public static final int USER_NAME_ILLEGAL = 20012;           //用户昵称有非法词
    public static final int USER_LOG_ILLEGAL = 20013;            //用户简介有非法词
    public static final int ISSUE_ROLE_NOT_FOUNT = 20101;            //角色不存在
    public static final int ISSUE_WORKS_NOT_FOUNT = 20102;            //原作不存在
}
