package isobar.panasonic.utility;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.*;

/**
 * Created by hieu.nguyen on 3/9/2018.
 */

public class FTPUtility {
    private String server, user, pass;
    private int port;
    private FTPClient ftpClient;
    private File file;
    private FileInputStream fileInputStream;
    private OutputStream outputStream;

    public FTPUtility(String server, int port, String user, String pass) {
        this.server = server;
        this.port = port;
        this.user = user;
        this.pass = pass;
        ftpClient = new FTPClient();
    }

    public void connect() {
        try {
            if (port == -1) {
                ftpClient.connect(server);
            } else {
                ftpClient.connect(server, port);
            }
            ftpClient.login(user, pass);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void disconnect() {
        try {
            if (ftpClient.isConnected()) {
                ftpClient.logout();
                ftpClient.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean dowloadSingleFile(String local, String remote) {
        try {
            file = new File(local);
            outputStream = new BufferedOutputStream(new FileOutputStream(file));
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            boolean status = ftpClient.retrieveFile(remote, outputStream);
            outputStream.close();
            return status;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean dowloadDirectory(String local, String remote) {
        try {
            String fileName;
            FTPFile[] subFiles = ftpClient.listFiles(remote);
            if (subFiles != null && subFiles.length != -1) {
                for (FTPFile subFile : subFiles) {
                    fileName = subFile.getName();
                    if (fileName.equals(".") || fileName.equals("src/test"))
                        continue;

                    if (subFile.isDirectory()) {
                        new File(local + "/" + fileName).mkdir();
                        dowloadDirectory(local + "/" + fileName, remote + "/" + fileName);
                    } else if (!dowloadSingleFile(local + "/" + fileName, remote + "/" + fileName))
                        return false;
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean uploadSingleFile(String local, String remote) {
        try {
            boolean status;
            file = new File(local);
            fileInputStream = new FileInputStream(file);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            status = ftpClient.storeFile(remote, fileInputStream);
            fileInputStream.close();
            return status;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean uploadDirectory(String local, String remote) {
        try {
            file = new File(local);
            String fileName;
            File[] subFiles = file.listFiles();
            boolean status;

            if (subFiles != null && subFiles.length != -1) {
                for (File subFile : subFiles) {
                    fileName = subFile.getName();

                    if (subFile.isDirectory()) {
                        ftpClient.makeDirectory(remote + "/" + fileName);
                        uploadDirectory(subFile.getAbsolutePath(), remote + "/" + fileName);
                    } else {
                        status = uploadSingleFile(subFile.getAbsolutePath(), remote + "/" + fileName);
                        if (!status)
                            return false;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void deleteRemoteFile(String remote) {
        try {
            ftpClient.deleteFile(remote);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteRemoteDirectory(String remote) {
        try {
            String fileName;
            FTPFile[] subFiles = ftpClient.listFiles(remote);
            if (subFiles != null && subFiles.length != -1) {
                for (FTPFile subFile : subFiles) {
                    fileName = subFile.getName();
                    if (fileName.equals(".") || fileName.equals("src/test"))
                        continue;

                    if (subFile.isDirectory()) {
                        deleteRemoteDirectory(remote + "/" + fileName);
                    } else {
                        deleteRemoteFile(remote + "/" + fileName);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}