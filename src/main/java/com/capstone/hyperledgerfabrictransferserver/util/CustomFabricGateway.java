package com.capstone.hyperledgerfabrictransferserver.util;

import com.capstone.hyperledgerfabrictransferserver.aop.customException.IncorrectFabricConnectException;
import com.fasterxml.jackson.core.exc.InputCoercionException;
import org.hyperledger.fabric.gateway.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class CustomFabricGateway {
    static {
        System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
    }

    public Gateway connect() {
        try {
            // Load a file system based wallet for managing identities.
            Path walletPath = Paths.get("wallet");
            Wallet wallet = Wallets.newFileSystemWallet(walletPath);
            // load a CCP
//        Path networkConfigPath = Paths.get("","Users", "jeongjaeyeob", "go", "src", "github.com", "Jaeyeop-Jung", "fabric-samples", "test-network", "organizations", "peerOrganizations", "org1.example.com", "connection-org1.yaml");
            Path networkConfigPath = Paths.get("/Users/jeongjaeyeob/go/src/github.com/Jaeyeop-Jung/fabric-samples/test-network/organizations/peerOrganizations/org1.example.com/connection-org1.yaml");

            Gateway.Builder builder = Gateway.createBuilder();
            builder.identity(wallet, "appUser").networkConfig(networkConfigPath).discovery(true);
            return builder.connect();
        } catch (IOException e){
            throw new IncorrectFabricConnectException("하이퍼레저 패브릭 연결 파일 읽기에 실패했습니다");
        }
    }

}
