package by.gruca.cafe.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

@SuppressWarnings("serial")
public class CopyrightTag extends TagSupport {

    private static final String COPYRIGHT_TAG = "Made by JJ93 \u00a9 . All rights reserved 2020.";

    @Override
    public int doStartTag() throws JspException {
        try {
            pageContext.getOut().write(COPYRIGHT_TAG);
        } catch (IOException e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

}