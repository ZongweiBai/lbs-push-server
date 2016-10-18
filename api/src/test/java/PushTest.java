import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.IQueryResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.guotion.pushserver.service.bean.Location;
import com.guotion.pushserver.service.entity.AliasAccount;
import com.guotion.pushserver.service.service.AliasAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;

/**
 * Created by baymin on 2016/5/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class PushTest {

    @Autowired
    private AliasAccountService aliasAccountService;

    @Test
    public void testPushToOne() {
        try {
            IGtPush push = new IGtPush("http://sdk.open.api.igexin.com/apiex.htm", "kM1fwStx259d8ko3E0gro6", "0vwAwYleyr6QAHHcjdNso6");
            push.connect();
            TransmissionTemplate template = new TransmissionTemplate();
            template.setTransmissionType(2);
            template.setAppId("GDZybPGsFHAKPOW6N1zh17");
            template.setTransmissionContent("代码测试一下咯123西平测试");
            template.setAppkey("kM1fwStx259d8ko3E0gro6");

            IQueryResult queryResult = push.getClientIdStatus("GDZybPGsFHAKPOW6N1zh17", "bae207e592255ecc27d8336caf14e076");
            System.out.println(queryResult.getResponse().toString());
            SingleMessage singleMessage = new SingleMessage();
            singleMessage.setOffline(true);
            singleMessage.setOfflineExpireTime(86400000L);

            singleMessage.setData(template);

            Target target = new Target();
            target.setClientId("bae207e592255ecc27d8336caf14e076");
            target.setAppId("GDZybPGsFHAKPOW6N1zh17");

            IPushResult result = push.pushMessageToSingle(singleMessage, target);
            System.out.println(result.getResponse().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLocation() {
        Location location = new Location(113.36365, 23.130035);
        List<AliasAccount> aliasAccounts = aliasAccountService.getAround(location, 20.0);
        for (AliasAccount aliasAccount : aliasAccounts) {
            System.out.println(aliasAccount.getAlias());
        }
    }

}
