package com.jeesite.modules.common.utils;

import com.jcraft.jsch.*;
import com.jeesite.modules.frp.entity.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
public class ShellUtil {

    public static final int DEFAULT_SSH_PORT = 22;
    protected Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 执行shell命令
     * @param command
     * @return
     */
    public int execute(Shell shell, final String command) {
        int returnCode = 0;
        JSch jsch = new JSch();


        try {

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            //创建session并且打开连接，因为创建session之后要主动打开连接
            Session session = jsch.getSession(shell.getUsername(), shell.getIp(), DEFAULT_SSH_PORT);
            session.setPassword(shell.getPassword());
            session.setConfig(config);
            session.connect();

            //打开通道，设置通道类型，和执行的命令
            Channel channel = session.openChannel("exec");
            ChannelExec channelExec = (ChannelExec)channel;
            channelExec.setCommand(command);

            channelExec.setInputStream(null);
            BufferedReader input = new BufferedReader(new InputStreamReader
                    (channelExec.getInputStream()));

            channelExec.connect();
            logger.info("The remote command is :{}" , command);

            //接收远程服务器执行命令的结果
            String line;
            while ((line = input.readLine()) != null) {
                shell.getStdout().add(line);
            }
            input.close();

            // 得到returnCode
            if (channelExec.isClosed()) {
                returnCode = channelExec.getExitStatus();
            }

            // 关闭通道
            channelExec.disconnect();
            //关闭session
            session.disconnect();

        } catch (JSchException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException("服务器连接异常：" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnCode;
    }
}
