/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadleafcommerce.seo.web.processor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.broadleafcommerce.core.web.processor.extension.HeadProcessorExtensionListener;
import org.broadleafcommerce.seo.domain.catalog.TwitterData;
import org.broadleafcommerce.seo.domain.catalog.TwitterDataImpl;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.exceptions.TemplateProcessingException;
import org.thymeleaf.standard.expression.StandardExpressionProcessor;

import java.util.Map;

/**
 * An extension of the head processor that will add the Twitter Data to the head context.
 * 
 * @author Jerry Ocanas (jocanas)
 */
public class TwitterDataHeadProcessorExtensionListener implements HeadProcessorExtensionListener {
    private static final Log LOG = LogFactory.getLog(TwitterDataHeadProcessorExtensionListener.class);

    public void processAttributeValues(Arguments arguments, Element element) {

        String dataObject = element.getAttributeValue("seoData");
        TwitterData twitterData = null;

		try {
            if(dataObject != null){
                Object rawDataObject = StandardExpressionProcessor.processExpression(arguments, dataObject);
                if(rawDataObject instanceof TwitterData){
                    TwitterData twitterDataObject = (TwitterData) rawDataObject;
                    twitterData = new TwitterDataImpl();
                    twitterData.setTwitterCard(twitterDataObject.getTwitterCard());
                    twitterData.setTwitterCreator(twitterDataObject.getTwitterCreator());
                    twitterData.setTwitterDescription(twitterDataObject.getTwitterDescription());
                    twitterData.setTwitterImage(twitterDataObject.getTwitterImage());
                    twitterData.setTwitterSite(twitterDataObject.getTwitterSite());
                    twitterData.setTwitterTitle(twitterDataObject.getTwitterTitle());
                    twitterData.setTwitterUrl(twitterDataObject.getTwitterUrl());
                }
            }
		} catch (TemplateProcessingException e) {
            LOG.error("Error processing expression", e);
		}

        ((Map<String, Object>) arguments.getExpressionEvaluationRoot()).put("twitterData", twitterData);

    }

}