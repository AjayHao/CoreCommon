package com.ajayhao.core.util;

import com.ajayhao.core.enumtype.BizCode;
import com.ajayhao.core.exception.BaseException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.net.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 平台通用辅助功能
 *
 */
public abstract class CoreSystemUtils {
    private static final Logger LOG = LoggerFactory.getLogger(CoreSystemUtils.class);

    private CoreSystemUtils() {
        ; // nothing.
    }

    /**
     * 当前JVM进程的id<br/>
     */
    private static int pid = -1;

    /**
     * 当前OS的ip地址（V4）<br/>
     */
    private static String localIp = null;

    /**
     * 当前OS的Host Name<br/>
     */
    private static String localHostName = null;

    /**
     * 当前OS的ip地址列表（V4）<br/>
     */
    private static Set<String> localIpSet = Collections.emptySet();

    /**
     * 当前machine的mac地址<br/>
     */
    private static String mac = null;

    /**
     * 自增<br/>
     */
    private static AtomicLong inc = new AtomicLong(0L);

    /**
     * 获取当前进程id<br/>
     *
     * @return
     */
    public static int pid() {
        return pid;
    }

    /**
     * 获取当前机器的Ip地址<br/>
     *
     * @return
     */
    public static String localIp() {
        return localIp;
    }

    /**
     * 获取本机的host name<br/>
     *
     * @return
     */
    public static String localHostName() {
        return localHostName;
    }

    public static Set<String> localIpSet() {
        return localIpSet;
    }

    public static String mac() {
        return mac;
    }

    /**
     * 生成12byte字符串的唯一id号<br/>
     * <pre>
     *     -------------------------------------
     *     |  time  |   mac  |  pid   |   inc  |
     *     |-----------------------------------|
     *     | 8 char | 6 char | 4 char | 6 char |
     *     -------------------------------------
     * </pre>
     *
     * @return
     */
    public static String objectId() {
        // 4 byte时间，占8个字符
        long time = System.currentTimeMillis();
        String timestamp = Integer.toHexString((int) (time & 0X7FFFFFFF));
        timestamp = StringUtils.right(timestamp, 8);
        timestamp = StringUtils.leftPad(timestamp, 8, "0");

        // 3 byte mac, 占6个字符
        String mac = StringUtils.right(mac(), 6);

        // 2 byte pid, 占4个字符
        int pid = pid();
        String $pid = Integer.toHexString(pid);
        $pid = StringUtils.right($pid, 4);
        $pid = StringUtils.leftPad($pid, 4, "0");

        // 3 byte inc, 占6个字符
        long inc = CoreSystemUtils.inc.getAndAdd(1L);
        inc = inc & 0X7FFFFFFFFFFFFFFFL; // 避免负数
        String $inc = Long.toHexString(inc % 0X7FFFFF);
        $inc = StringUtils.right($inc, 6);
        $inc = StringUtils.leftPad($inc, 6, "0");

        return timestamp.toLowerCase() + mac.toLowerCase()
                + $pid.toLowerCase() + $inc.toLowerCase();
    }

    /**
     * 加上前后最 log在拼接的时候防止错误
     * 如 1   111xxx111  11
     * 11   111xxx111  1 会区分不开
     * 生成16byte字符串的唯一id号<br/>
     * <pre>
     *     -------------------------------------
     *     |  time  |   mac  |  pid   |   inc  |
     *     |-----------------------------------|
     *     | 8 char | 6 char | 4 char | 6 char |
     *     -------------------------------------
     * </pre>
     *
     * @return
     */
    public static String objectIdB(String preFix, String subFox) {
        // 4 byte时间，占8个字符
        long time = System.currentTimeMillis();
        String timestamp = Integer.toHexString((int) (time & 0X7FFFFFFF));
        timestamp = StringUtils.right(timestamp, 8);
        timestamp = StringUtils.leftPad(timestamp, 8, "0");

        // 3 byte mac, 占6个字符
        String mac = StringUtils.right(mac(), 6);

        // 2 byte pid, 占4个字符
        int pid = pid();
        String $pid = Integer.toHexString(pid);
        $pid = StringUtils.right($pid, 4);
        $pid = StringUtils.leftPad($pid, 4, "0");

        // 3 byte inc, 占6个字符
        long inc = CoreSystemUtils.inc.getAndAdd(1L);
        inc = inc & 0X7FFFFFFFFFFFFFFFL; // 避免负数
        String $inc = Long.toHexString(inc % 0X7FFFFF);
        $inc = StringUtils.right($inc, 6);
        $inc = StringUtils.leftPad($inc, 6, "0");

        return preFix + timestamp.toLowerCase() + mac.toLowerCase()
                + $pid.toLowerCase() + $inc.toLowerCase() + subFox;
    }

    /**
     * 明文形式的主键生成算法<br/>
     * <p>
     * <pre>
     *     ----------------------------------------------------------
     *     |  prefix |   time  | userId  |   mac  |  pid   |   inc  |
     *     |--------------------------------------------------------|
     *     |  3 char | 14 char |  2 char | 4 char | 4 char | 5 char |
     *     ----------------------------------------------------------
     * </pre>
     *
     * @param prefix    前缀，只使用前3位字符
     * @param partition 分区标识，只使用后2位字符
     * @return
     */
    public static String objectId32(String prefix, String partition) {
        prefix = StringUtils.left(prefix, 3);
        prefix = StringUtils.leftPad(prefix, 3, "0");

        // 时间日期明文，占14个字符
        Calendar dateTime = getCurrentCalendar();
        String timestamp = dateTime.get(Calendar.YEAR)
                + "" + StringUtils.leftPad((dateTime.get(Calendar.MONTH) + 1) + "", 2, "0")
                + "" + StringUtils.leftPad(dateTime.get(Calendar.DAY_OF_MONTH) + "", 2, "0")
                + "" + StringUtils.leftPad(dateTime.get(Calendar.HOUR_OF_DAY) + "", 2, "0")
                + "" + StringUtils.leftPad(dateTime.get(Calendar.MINUTE) + "", 2, "0")
                + "" + StringUtils.leftPad(dateTime.get(Calendar.SECOND) + "", 2, "0");

        partition = StringUtils.right(partition, 2);
        partition = StringUtils.leftPad(partition, 2, "0");

        // 3 byte mac, 占6个字符
        String mac = StringUtils.right(mac(), 4);

        // 2 byte pid, 占4个字符
        int pid = pid();
        String $pid = Integer.toHexString(pid);
        $pid = StringUtils.right($pid, 4);
        $pid = StringUtils.leftPad($pid, 4, "0");

        // 3 byte inc, 占5个字符
        long inc = CoreSystemUtils.inc.getAndAdd(1L);
        inc = inc & 0X7FFFFFFFFFFFFFFFL; // 避免负数
        String $inc = Long.toHexString(inc % 0X7FFFF);
        $inc = StringUtils.right($inc, 5);
        $inc = StringUtils.leftPad($inc, 5, "0");

        return prefix.toLowerCase() + timestamp.toLowerCase()
                + partition.toLowerCase() + mac.toLowerCase()
                + $pid.toLowerCase() + $inc.toLowerCase();
    }

    /**
     * 获取当前系统日期时间<br/>
     *
     * @return
     */
    private static Calendar getCurrentCalendar() {
        return Calendar.getInstance();
    }

    // ------------------------------------------------ 系统常量初始化block
    static {
        initPID(); // 初始化进程id；

        initLocalIpListAndMac(); // 当前OS的ip地址列表和MAC信息；

        // 当前OS的Host Name；
        initLocalHostName();
    }

    private static void initLocalHostName() {
        try {
            final String[] cells = localIp.split("\\.");
            byte[] bs = new byte[cells.length];
            for (int i = 0; i < bs.length; ++i) {
                bs[i] = (byte) (Integer.parseInt(cells[i]) & 0xFF);
            }
            InetAddress ia = InetAddress.getByAddress(bs);

            localHostName = ia.getHostName();
        } catch (UnknownHostException e) {
            throw new BaseException(BizCode.INVALID_PARAM);
        }
    }

    /**
     * 初始化本地IP地址和MAC信息<br/>
     */
    private static void initLocalIpListAndMac() {
        try {
            doInitLocalIpListAndMac();
        } catch (Exception e) {
            LOG.warn("Get local machine ip and mac information error.", e);
        }
    }

    private static void doInitLocalIpListAndMac() throws Exception {
        // 获取本地网络接口列表
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

        while (interfaces.hasMoreElements()) {
            NetworkInterface inter = interfaces.nextElement();
            byte[] hardwareBin = inter.getHardwareAddress();

            // 不要依赖ArrayUtils，减少对外部库的依赖
            if (hardwareBin == null || hardwareBin.length <= 0) {
                continue;
            }

            processNetworkInter(inter);
        }
    }

    private static void processNetworkInter(NetworkInterface inter) {
        // 获取网络接口的所有ip地址并进行处理
        Enumeration<InetAddress> inetAddressEnum = inter.getInetAddresses();

        Set<String> $localIpSet = new HashSet<String>(4, 1F);
        while (inetAddressEnum.hasMoreElements()) {
            InetAddress inetAddress = inetAddressEnum.nextElement();
            String hostAddress = inetAddress.getHostAddress();

            // 排除loopback(127.0.0.1)和wildcard(0.0.0.0)地址
            if (inetAddress.isAnyLocalAddress() || inetAddress.isLoopbackAddress()) {
                continue;
            }

            // 排除ipv6地址
            if (inetAddress instanceof Inet6Address) {
                continue;
            }

            byte[] hardwareBin = new byte[0];
            try {
                NetworkInterface networkInter = NetworkInterface.getByInetAddress(inetAddress);
                hardwareBin = networkInter.getHardwareAddress();
                // 不要依赖ArrayUtils，减少对外部库的依赖
                if (hardwareBin == null || hardwareBin.length <= 0) {
                    continue;
                }
            } catch (SocketException e) {
                LOG.warn("internal error.", e);
                continue;
            }

            // 第一个ipv4地址作为默认ip地址
            if (StringUtils.isEmpty(localIp)) {
                localIp = hostAddress;
                mac = DigestUtils.md5Hex(hardwareBin);
            }

            // 添加到ipv4地址列表
            $localIpSet.add(hostAddress);
        }

        // 保证ip地址列表不可变；
        $localIpSet.addAll(localIpSet);
        localIpSet = Collections.unmodifiableSet($localIpSet);
    }

    /**
     * 初始当前进程（process）的id<br/>
     */
    private static void initPID() {
        try {
            String name = ManagementFactory.getRuntimeMXBean().getName();

            int index = name.indexOf("@");
            pid = Integer.parseInt(name.substring(0, index));

            return;
        } catch (Exception e) {
            LOG.warn("Get process id error.", e);
        }

        pid = new Random().nextInt();
    }
}
