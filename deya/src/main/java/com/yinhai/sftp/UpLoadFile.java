package com.yinhai.sftp;

import com.jcraft.jsch.*;

import java.io.*;

/**
 * @Description:
 * @User: Administrator
 * @Date: 2018/5/8 0008
 */
public class UpLoadFile {

    public static void sshSftpUpload(String ip, String user, String psw, int port,
                               String sPath, String dPath) {
        System.out.println("password login");
        Session session = null;

        JSch jsch = new JSch();
        try {
            if (port <= 0) {
                // 连接服务器，采用默认端口
                session = jsch.getSession(user, ip);
            } else {
                // 采用指定的端口连接服务器
                session = jsch.getSession(user, ip, port);
            }

            // 如果服务器连接不上，则抛出异常
            if (session == null) {
                throw new Exception("session is null");
            }

            // 设置登陆主机的密码
            session.setPassword(psw);// 设置密码
            // 设置第一次登陆的时候提示，可选值：(ask | yes | no)
            session.setConfig("StrictHostKeyChecking", "no");
            // 设置登陆超时时间
            session.connect(300000);
            upLoadFile(session, sPath, dPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("success");
    }

    public static void upLoadFile(Session session, String sPath, String dPath) {

        Channel channel = null;
        try {
            channel = (Channel) session.openChannel("sftp");
            channel.connect(10000000);
            ChannelSftp sftp = (ChannelSftp) channel;
            try {
                sftp.cd(dPath);
            } catch (SftpException e) {
                sftp.mkdir(dPath);
                sftp.cd(dPath);
            }
            File file = new File(sPath);
            copyFile(sftp, file, sftp.pwd());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.disconnect();
            channel.disconnect();
        }
    }

    public static void copyFile(ChannelSftp sftp, File file, String pwd) {

        if (file.isDirectory()) {
            File[] list = file.listFiles();
            try {
                try {
                    String fileName = file.getName();
                    sftp.cd(pwd);
                    System.out.println("正在创建目录:" + sftp.pwd() + "/" + fileName);
                    sftp.mkdir(fileName);
                    System.out.println("目录创建成功:" + sftp.pwd() + "/" + fileName);
                } catch (Exception e) {
                    // TODO: handle exception
                }
                pwd = pwd + "/" + file.getName();
                try {

                    sftp.cd(file.getName());
                } catch (SftpException e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            for (int i = 0; i < list.length; i++) {
                copyFile(sftp, list[i], pwd);
            }
        } else {

            try {
                sftp.cd(pwd);

            } catch (SftpException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            System.out.println("正在复制文件:" + file.getAbsolutePath());
            InputStream instream = null;
            OutputStream outstream = null;
            try {
                outstream = sftp.put(file.getName());
                instream = new FileInputStream(file);

                byte b[] = new byte[1024];
                int n;
                try {
                    while ((n = instream.read(b)) != -1) {
                        outstream.write(b, 0, n);
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            } catch (SftpException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                try {
                    outstream.flush();
                    outstream.close();
                    instream.close();

                } catch (Exception e2) {
                    // TODO: handle exception
                    e2.printStackTrace();
                }
            }
        }
    }
}
