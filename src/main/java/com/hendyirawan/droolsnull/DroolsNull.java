/**
 * 
 */
package com.hendyirawan.droolsnull;

import java.util.Date;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseConfiguration;
import org.drools.KnowledgeBaseFactoryService;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactoryService;
import org.drools.builder.ResourceType;
import org.drools.builder.impl.KnowledgeBuilderFactoryServiceImpl;
import org.drools.impl.KnowledgeBaseFactoryServiceImpl;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ceefour
 *
 */
public class DroolsNull {
	private transient Logger log = LoggerFactory.getLogger(DroolsNull.class);

	private KnowledgeBuilderFactoryService kbuilderFactorySvc;
	private KnowledgeBaseFactoryService kbaseFactorySvc;
	
	public DroolsNull(KnowledgeBuilderFactoryService kbuilderFactorySvc,
			KnowledgeBaseFactoryService kbaseFactorySvc) {
		super();
		this.kbuilderFactorySvc = kbuilderFactorySvc;
		this.kbaseFactorySvc = kbaseFactorySvc;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new DroolsNull(new KnowledgeBuilderFactoryServiceImpl(),
				new KnowledgeBaseFactoryServiceImpl()).run();
	}
	
	public void run() {
		BookingService bookingService = new BookingService();
		
    	KnowledgeBuilder kbuilder = kbuilderFactorySvc.newKnowledgeBuilder();

        kbuilder.add(ResourceFactory.newClassPathResource("/null.drl", DroolsNull.class), ResourceType.DRL);
        
        KnowledgeBuilderErrors errors = kbuilder.getErrors();
        if (errors.size() > 0) {
            for (KnowledgeBuilderError error: errors) {
                log.error("Error parsing knowledge: {}", error);
            }
            throw new IllegalArgumentException("Could not parse knowledge.");
        }
        KnowledgeBaseConfiguration kbaseConf = kbaseFactorySvc.newKnowledgeBaseConfiguration(
        		null, getClass().getClassLoader());
        KnowledgeBase kbase = kbaseFactorySvc.newKnowledgeBase(kbaseConf);
        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());

        StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
//        ksession.addEventListener(new Slf4jAgendaEventListener());
//        ksession.addEventListener(new Slf4jWorkingMemoryEventListener());
        ksession.setGlobal("log", log);
		ksession.setGlobal("bookingService", bookingService);
        
        Booking nullBooking = new Booking(null);
        Booking filledBooking = new Booking(5.0d);
        
        Notify nullNotify = new Notify(null);
        Notify filledNotify = new Notify(new Date());
        
		ksession.insert(nullBooking);
		ksession.insert(filledBooking);
		ksession.insert(nullNotify);
		ksession.insert(filledNotify);
		
		ksession.fireAllRules();
		
	}

}
