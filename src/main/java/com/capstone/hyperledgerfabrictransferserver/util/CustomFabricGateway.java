package com.capstone.hyperledgerfabrictransferserver.util;

import com.capstone.hyperledgerfabrictransferserver.aop.customException.IncorrectFabricConnectException;
import com.fasterxml.jackson.core.exc.InputCoercionException;
import org.hyperledger.fabric.gateway.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class CustomFabricGateway {

    @Value("${hyperledger.CustomFabricGateway}")
    private String location;

    static {
        System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
    }

    public synchronized Gateway connect() {
        try {
            long start = System.currentTimeMillis();
            // Load a file system based wallet for managing identities.
            Path walletPath = Paths.get("/root/wallet");
            Wallet wallet = Wallets.newFileSystemWallet(walletPath);
            // load a CCP
//        Path networkConfigPath = Paths.get("","Users", "jeongjaeyeob", "go", "src", "github.com", "Jaeyeop-Jung", "fabric-samples", "test-network", "organizations", "peerOrganizations", "org1.example.com", "connection-org1.yaml");
            Path networkConfigPath = Paths.get(location);

            Gateway.Builder builder = Gateway.createBuilder();
            builder.identity(wallet, "appUser").networkConfig(networkConfigPath).discovery(true);
            Gateway gateway = builder.connect();

            return gateway;
        } catch (IOException e){
            throw new IncorrectFabricConnectException("하이퍼레저 패브릭 연결 파일 읽기에 실패했습니다");
        }
    }

}